package com.realdolmen.chiro;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TestApplication {

    // This means that this class is actually ignored.
    // Hypothesis: Only the @Bean annotations like in a real config class are considered.
    public static void main(String[] args) {
        new SpringApplicationBuilder(TestApplication.class)
                .bannerMode(Banner.Mode.OFF);
                //.run();
    }
}
