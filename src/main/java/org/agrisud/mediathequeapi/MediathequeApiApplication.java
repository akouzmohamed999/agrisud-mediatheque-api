package org.agrisud.mediathequeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
@EnableAsync
@SpringBootApplication
public class MediathequeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediathequeApiApplication.class, args);
    }

}
