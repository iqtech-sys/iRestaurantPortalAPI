package com.irestaurant.iPortalAPI.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "backgroundTaskExecutor")
    public Executor backgroundTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        
        // 1. Core Pool Size: Number of threads always kept alive.
        // Recommendation: Number of CPU Cores + 1
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors() + 1);

        // 2. Max Pool Size: Maximum threads allowed under heavy load.
        // Recommendation: 2x to 4x of CorePoolSize (depending on RAM)
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2);

        // 3. Queue Capacity: How many tasks wait before Max Pool kicks in.
        //Set Queue based on RAM (10% of Max Heap / Estimated 250KB per task)
        long maxMemory = Runtime.getRuntime().maxMemory(); // Returns bytes
        long taskSizeEstimate = 250 * 1024; // 250 KB
        int calculatedQueue = (int) ((maxMemory * 0.10) / taskSizeEstimate);
        executor.setQueueCapacity(Math.max(100, Math.min(calculatedQueue, 1000)));

        // 4. The "Safety Valve": What happens when the Queue AND MaxPool are full?
        // CallerRunsPolicy makes the requesting thread handle the task itself.
        // This naturally slows down the incoming request rate (Backpressure).
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.setThreadNamePrefix("iPortal-Async-");
        executor.initialize();
        return executor;
    }
}
