package com.xiaofei.li.permissionservice8004;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication (scanBasePackages = "com.xiaofei.li")
@EnableEurekaClient
public class PermissionService8004Application {

    public static void main(String[] args) {
        SpringApplication.run(PermissionService8004Application.class, args);
    }

}
