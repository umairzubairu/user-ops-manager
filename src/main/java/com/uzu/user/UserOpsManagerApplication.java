package com.uzu.user;

import jakarta.annotation.PostConstruct;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserOpsManagerApplication {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(UserOpsManagerApplication.class, args);
    }
}
