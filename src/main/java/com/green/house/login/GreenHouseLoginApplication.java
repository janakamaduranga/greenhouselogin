package com.green.house.login;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class GreenHouseLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreenHouseLoginApplication.class, args);
    }
}
