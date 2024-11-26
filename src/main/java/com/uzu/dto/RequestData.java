package com.uzu.dto;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.*;

@Data
public class RequestData {
    AtomicInteger requests = new AtomicInteger(0);
    Instant resetTime = Instant.now().plusSeconds(60);
}