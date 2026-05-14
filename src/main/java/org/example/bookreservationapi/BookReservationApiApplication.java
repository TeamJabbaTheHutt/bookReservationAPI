package org.example.bookreservationapi;

import org.example.bookreservationapi.security.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class BookReservationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookReservationApiApplication.class, args);
    }

}
