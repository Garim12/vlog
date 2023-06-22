package com.sparta.vlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(VlogApplication.class, args);
    }

}
