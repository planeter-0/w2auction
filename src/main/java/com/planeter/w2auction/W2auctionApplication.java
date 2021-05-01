package com.planeter.w2auction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class W2auctionApplication {
    public static void main(String[] args) {
        SpringApplication.run(W2auctionApplication.class, args);
    }

}
