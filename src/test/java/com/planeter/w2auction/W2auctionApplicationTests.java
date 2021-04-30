package com.planeter.w2auction;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootTest
@ServletComponentScan//启用 XssFilter
class W2auctionApplicationTests {

    @Test
    void contextLoads() {
    }

}
