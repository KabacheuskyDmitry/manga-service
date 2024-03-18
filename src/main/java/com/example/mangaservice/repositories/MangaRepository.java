package com.example.mangaservice.repositories;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MangaRepository {
    private final Map<String, Double> storage = new HashMap<>(Map.of("OnePiece", 4.83));

    public Double getRatingForName(String name)
    {
       return storage.get(name);
    }
}
