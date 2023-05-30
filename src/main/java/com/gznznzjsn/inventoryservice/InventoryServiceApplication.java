package com.gznznzjsn.inventoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryServiceApplication {

    /**
     * Main method of application, which will be invoked on startup
     * of application and will run Spring Boot Application.
     *
     * @param args application arguments, passed from startup command
     */
    public static void main(final String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

}
