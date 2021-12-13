package com.example.academicv2_0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class AcademicApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcademicApplication.class, args);
    }

}
