package com.irestaurant.iPortalAPI.util;

import generated.MyObjectBox;
import io.objectbox.BoxStore;
import io.objectbox.sync.Sync;
import io.objectbox.sync.SyncClient;
import io.objectbox.sync.SyncCredentials;
import io.objectbox.sync.listener.AbstractSyncListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;

/**
 * Thread-safe Singleton Manager for ObjectBox Sync.
 * Handles BoxStore initialization, SyncClient lifecycle, and error monitoring.
 */
@org.springframework.stereotype.Component
public class SyncManager {
    
    private static String syncDbPath;
    private static String syncServerUrl;
    
    @Value("${iPortalApi.syncDbPath}")
    public void setSyncDbPath(String path) {
        syncDbPath = path;
    }

    @Value("${iPortalApi.syncServerUrl:}")
    public void setSyncServerUrl(String url) {
        syncServerUrl = url;
    }

    @Value("${iPortalApi.syncSharedSecret:}")
    public void setSyncSharedSecret(String secret) {
    }
    
    private static final Logger logger = LoggerFactory.getLogger(SyncManager.class);
    private static SyncManager instance;
    private BoxStore boxStore;
    private SyncClient syncClient;

    private SyncManager() {
    }
    
    public static synchronized BoxStore init(String email){
        SyncManager syncManager = SyncManager.getInstance();
        if (syncManager.getBoxStore() == null) {
            String effectivePath = syncDbPath;
            if (effectivePath == null || effectivePath.isEmpty()) {
                effectivePath = "objectbox-db";
            }
            // Always initialize the BoxStore (database)
            syncManager.initBoxStore(effectivePath);
            // Optionally start sync if a server URL is configured
            if (syncServerUrl != null && !syncServerUrl.isEmpty()) {
                syncManager.startSync(syncServerUrl);
            }
        }
        return syncManager.getBoxStore();
    }
    
    /**
     * Gets the thread-safe instance of SyncManager.
     * @return 
     */
    public static synchronized SyncManager getInstance() {
        if (instance == null) {
            instance = new SyncManager();
        }
        return instance;
    }

    /**
     * Initializes the BoxStore only (no sync).
     * This allows the database to work standalone without a sync server.
     * 
     * @param dbPath Local path for the ObjectBox database.
     */
    public synchronized void initBoxStore(String dbPath) {
        if (boxStore != null) {
            return; // Already initialized
        }
        try {
            File dbDirectory = Paths.get(dbPath).toFile();
            if (!dbDirectory.exists()) {
                if (dbDirectory.mkdirs()) {
                   logger.info("Created database directory: {}", dbPath);
                }
            }
            boxStore = MyObjectBox.builder()
                                  .directory(dbDirectory)
                                  .build();
            logger.info("BoxStore initialized at: {}", dbPath);
        } catch (Exception e) {
            logger.error("Failed to initialize BoxStore: {}", e.getMessage(), e);
            throw new RuntimeException("BoxStore initialization failed", e);
        }
    }

    /**
     * Starts ObjectBox Sync with the given server.
     * This is optional — the BoxStore works without sync.
     * Errors are logged but do NOT crash the application.
     * 
     * @param serverUrl URL of the ObjectBox Sync server (e.g., ws://1.2.3.4:9999).
     */
    public synchronized void startSync(String serverUrl) {
        if (boxStore == null) {
            logger.error("Cannot start sync: BoxStore is not initialized. Call initBoxStore() first.");
            return;
        }
        try {
            // Stop existing client if any
            if (syncClient != null) {
                syncClient.stop();
            }
            syncClient = Sync.client(boxStore, serverUrl, SyncCredentials.none())
                             .listener(new AbstractSyncListener() {
                                @Override
                                public void onLoggedIn() {
                                    logger.info("✅ Sync Logged in successfully: {}", serverUrl);
                                }

                                @Override
                                public void onLoginFailed(long code) {
                                    logger.error("❌ Sync Login failed with code: {}. Check credentials and server URL.", code);
                                }

                                @Override
                                public void onDisconnected() {
                                    logger.warn("⚠️ Sync disconnected from server.");
                                }
                              })
                             .buildAndStart();
            logger.info("SyncClient started for server: {}", serverUrl);
        } catch (Exception e) {
            logger.error("Failed to start sync: {}", e.getMessage(), e);
            // Log the error but do NOT crash — the BoxStore still works without sync
            logger.warn("⚠️ ObjectBox Sync could not start (server may be unavailable): {}", e.getMessage());
        }
    }

    /**
     * Starts the synchronization process.
     */
    public synchronized void start() {
        if (syncClient != null) {
            syncClient.start();
            logger.info("ObjectBox Sync process started.");
        } else {
            logger.error("SyncClient is not initialized. Call initialize() first.");
        }
    }

    /**
     * Stops the synchronization process and closes the BoxStore.
     */
    public synchronized void stop() {
        if (syncClient != null) {
            syncClient.stop();
            syncClient = null;
            logger.info("ObjectBox Sync process stopped.");
        }
        if (boxStore != null) {
            boxStore.close();
            boxStore = null;
            logger.info("BoxStore closed.");
        }
    }

    /**
     * Provides access to the BoxStore for other services.
     */
    private BoxStore getBoxStore() {
        return boxStore;
    }

    /**
     * Checks if the SyncClient is currently active.
     */
    public boolean isSyncing() {
        return syncClient != null && syncClient.isStarted();
    }
}
