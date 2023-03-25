package com.example.demo.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AppConfig {

//    @Value("${v.count}")
    private int reqCount;

//    @Value("${v.queue}")
    private String queue;

}
