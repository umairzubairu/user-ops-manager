package com.uzu.interceptor;

import com.uzu.dto.RequestData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

    private static final int MAX_REQUESTS = 10; // Max requests per minute
    private final Map<String, RequestData> requestCounts = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIP = request.getRemoteAddr(); // Use IP for simplicity, adapt for tokens if needed

        RequestData userData = requestCounts.computeIfAbsent(clientIP, key -> new RequestData());

        synchronized (userData) {
            if (Instant.now().isAfter(userData.getResetTime())) {
                userData.setResetTime(Instant.now().plusSeconds(30)); // Reset every 30 seconds
                userData.setRequests(new AtomicInteger(0));
            }

            if (userData.getRequests().incrementAndGet() > MAX_REQUESTS) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write("Rate limit exceeded. Try again later.");
                return false;
            }
        }
        return true;
    }
}
