package com.example.mangaservice.components;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Component
public class RequestCounter {
    private final AtomicInteger counter = new AtomicInteger(0);

    public synchronized void incrementAndGet() {
        counter.incrementAndGet();
    }
    public int getCurrentCount() {
        return counter.get();
    }
}