package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity(/*debug = true*/)
public class SpringBoot3AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot3AuthorizationServerApplication.class, args);
    }

}
