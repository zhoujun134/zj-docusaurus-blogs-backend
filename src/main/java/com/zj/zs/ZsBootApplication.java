package com.zj.zs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ServletComponentScan
@EnableAsync
public class ZsBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZsBootApplication.class, args);
    }

}
