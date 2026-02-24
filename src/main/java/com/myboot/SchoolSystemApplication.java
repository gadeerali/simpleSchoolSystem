package com.myboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableKafka
@EnableScheduling
@EnableSpringDataWebSupport
@SpringBootApplication
public class SchoolSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolSystemApplication.class, args);
    }

}



