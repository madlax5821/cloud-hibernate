package com.xiaofei.li.roleservice8003;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication (scanBasePackages = "com.xiaofei.li")
@EnableEurekaClient
public class RoleService8003Application {

    public static void main(String[] args) {
        SpringApplication.run(RoleService8003Application.class, args);
    }

}
