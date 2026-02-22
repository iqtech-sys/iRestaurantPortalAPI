package com.irestaurant.iPortalAPI.util;

import com.irestaurant.iPortalAPI.generated.MyObjectBox;
import io.objectbox.BoxStore;
import java.io.File;
import org.springframework.beans.factory.annotation.Value;

public class ObjectboxManager {

    private static BoxStore store;
    
    @Value("${iPortalApi.syncDbPath}")
    private static final String syncDbPath = "";

    public static void init(String email) {
        File dbDirectory = new File(syncDbPath);
        store = MyObjectBox.builder()
                           .directory(dbDirectory)
                           .build();
    }

    public static BoxStore get() {
        return store;
    }
    
    public static void close() {
        if(store != null) {
           store.close();
        }
    }
}
