package com.shopme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.shopme.common.entity"})
public class ShopmeBackendRunner {
    public static void main (String[] args){
        SpringApplication.run(ShopmeBackendRunner.class,args);
    }
}
