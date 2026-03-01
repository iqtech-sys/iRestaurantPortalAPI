package com.irestaurant.iPortalAPI.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SyncJobService {

    // Instantiate SLF4J Logger
    private static final Logger logger = LoggerFactory.getLogger(SyncJobService.class);

    public void processSync() {
        // Will go to the CONSOLE only (not the ERROR file)
        logger.info("Started synchronization process...");

        try {
            // Simulating a runtime exception
            int numerator = 100;
            int denominator = 0;
            int result = numerator / denominator;

            logger.info("Synchronization calculation successful: {}", result);

        } catch (Exception e) {
            // ✨ This gets successfully caught and printed to CONSOLE and the ERROR_FILE
            // Passing the Exception 'e' as the last argument ensures the Stack Trace is
            // logged
            logger.error("Failed to process synchronization due to an unexpected error.", e);
        }

        // Will go to the CONSOLE only
        logger.warn("Synchronization task completed, but encountered issues.");
    }
}
