package com.irestaurant.iPortalAPI;

//import com.irestaurant.iPortalAPI.util.Objectbox;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync(proxyTargetClass = true)
@SpringBootApplication
public class IPortalApiApplication {

    public static void main(String[] args) {
        //Objectbox.init("iRestaurant-db");
        SpringApplication.run(IPortalApiApplication.class, args);
    }

}
