package com.learning.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CourseManagementSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseManagementSystemApplication.class, args);
    }
}
