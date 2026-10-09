package com.ecopulse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EcoPulseApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcoPulseApplication.class, args);
        System.out.println("=================================================");
        System.out.println("🌱 EcoPulse Backend started successfully on port 8080");
        System.out.println("🌿 Monitor. Understand. Act. Together for a Greener Future.");
        System.out.println("=================================================");
    }
}
