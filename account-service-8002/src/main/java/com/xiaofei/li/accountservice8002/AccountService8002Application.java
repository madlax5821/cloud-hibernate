package com.xiaofei.li.accountservice8002;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication (scanBasePackages = "com.xiaofei.li")
@EnableEurekaClient
@EnableFeignClients (basePackages = "com.xiaofei.li.service")
public class AccountService8002Application {

    public static void main(String[] args) {
        SpringApplication.run(AccountService8002Application.class, args);
    }

}
