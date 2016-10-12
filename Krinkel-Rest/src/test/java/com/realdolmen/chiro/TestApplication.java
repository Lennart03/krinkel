package com.realdolmen.chiro;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TestApplication.class)
                .bannerMode(Banner.Mode.OFF) // Well this seems to be ignored. That's totally fine.
                                             // Good job Spring, go have some coffee...
                                             // Then go and shoot yourself.
                .run();
    }
}
