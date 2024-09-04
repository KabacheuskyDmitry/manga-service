package com.example.mangaservice.controllers;

import com.example.mangaservice.components.RequestCounter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterController {

    private final RequestCounter requestCounter;

    public CounterController(RequestCounter requestCounter) {
        this.requestCounter = requestCounter;
    }

    @GetMapping("/count")
    public String getCount() {
        return "Current count: " + requestCounter.getCurrentCount();
    }
}