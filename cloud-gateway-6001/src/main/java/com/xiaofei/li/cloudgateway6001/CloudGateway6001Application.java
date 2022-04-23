package com.xiaofei.li.cloudgateway6001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CloudGateway6001Application {

    public static void main(String[] args) {
        SpringApplication.run(CloudGateway6001Application.class, args);
    }

}
