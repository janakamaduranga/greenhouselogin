package com.green.house.login;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEncryptableProperties
@EnableEurekaClient
public class GreenHouseLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreenHouseLoginApplication.class, args);
    }
}
