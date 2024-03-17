package com.zj.zs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class ZsBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZsBootApplication.class, args);
    }

}
