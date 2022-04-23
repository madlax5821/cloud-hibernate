package com.xiaofei.li.userservice8001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication (scanBasePackages = "com.xiaofei.li")
@EnableFeignClients (basePackages = "com.xiaofei.li.service")
@EnableEurekaClient
public class UserService8001Application {

    public static void main(String[] args) {
        SpringApplication.run(UserService8001Application.class, args);
    }

}
