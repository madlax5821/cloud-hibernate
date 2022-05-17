package com.xiaofei.li.cloudgateway6001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication (scanBasePackages = "com.xiaofei.li")
@EnableDiscoveryClient
public class CloudGateway6001Application {

    public static void main(String[] args) {
        SpringApplication.run(CloudGateway6001Application.class, args);
    }

}
