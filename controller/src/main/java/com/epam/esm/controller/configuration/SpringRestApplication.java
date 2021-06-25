package com.epam.esm.controller.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackages = "com.epam.esm")
public class SpringRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestApplication.class, args);
    }

}
