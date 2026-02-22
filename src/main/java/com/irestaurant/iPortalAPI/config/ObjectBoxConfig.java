//package com.irestaurant.iPortalAPI.config;
//
////import com.irestaurant.iPortalAPI.generated.MyObjectBox;
//import com.irestaurant.iPortalAPI.generated.MyObjectBox;
//import io.objectbox.BoxStore;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
///**
// * ObjectBox configuration class for Spring Boot application.
// * Provides a singleton instance of BoxStore for thread-safe database
// * operations.
// */
//@Configuration
//public class ObjectBoxConfig {
//
//    private static volatile BoxStore boxStore;
//
//    /**
//     * Initializes the ObjectBox BoxStore as a singleton Spring bean.
//     * The database file is named "iRestaurant-db" and stored in the default
//     * location.
//     * 
//     * @return The singleton BoxStore instance
//     */
//    @Bean
//    @Primary
//    public BoxStore boxStore() {
//        if (boxStore == null) {
//            synchronized (ObjectBoxConfig.class) {
//                if (boxStore == null) {
//                    try {
//                        // Initialize ObjectBox using the generated MyObjectBox class
//                        boxStore = MyObjectBox.builder()
//                                              .name("C:\\Data\\Freelancing\\iRestaurant\\App\\Prod\\iPortalAPI-Prod\\iPortalAPI\\src\\main\\resources\\iRestaurant-db")
//                                              .build();
//                    } catch (Exception e) {
//                        throw new RuntimeException(
//                                "Failed to initialize ObjectBox. Ensure MyObjectBox class is generated. " +
//                                        "Run 'mvn clean compile'. " +
//                                        "Original error: " + e.getMessage(),
//                                e);
//                    }
//                }
//            }
//        }
//        return boxStore;
//    }
//
//    /**
//     * Gets the singleton BoxStore instance.
//     * This method provides direct access to the BoxStore without going through
//     * Spring beans.
//     * 
//     * @return The singleton BoxStore instance
//     */
//    public static BoxStore getBoxStore() {
//        if (boxStore == null) {
//            throw new IllegalStateException("ObjectBox has not been initialized. " +
//                    "Ensure ObjectBoxConfig is loaded by Spring.");
//        }
//        return boxStore;
//    }
//
//    /**
//     * Shuts down the BoxStore gracefully.
//     * This method should be called during application shutdown.
//     */
//    public static void shutdown() {
//        if (boxStore != null) {
//            boxStore.close();
//            boxStore = null;
//        }
//    }
//}
