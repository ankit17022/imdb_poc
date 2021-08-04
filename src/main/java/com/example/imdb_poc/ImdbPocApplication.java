package com.example.imdb_poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@EnableAsync
public class ImdbPocApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImdbPocApplication.class, args);
    }

}
