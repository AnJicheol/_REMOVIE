package com.example.removie_read_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class RemovieReadServerApplication {

    public static void main(String[] args) {

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ALLOWED_IP: " + System.getenv("DLRJTEHDKSEHLSIQUDTLSRKXDMSTORRLDI"));
        System.out.println("allowed.ip (Spring): " + System.getProperty("SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL"));

        SpringApplication.run(RemovieReadServerApplication.class, args);
    }

}
