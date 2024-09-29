package com.itciod.goosesystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
@MapperScan("com.itciod.goosesystem.mapper")
public class GooseSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(GooseSystemApplication.class, args);
    }}
