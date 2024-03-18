package com.example.mangaservice.services;

import com.example.mangaservice.repositories.MangaRepository;
import org.springframework.stereotype.Service;

@Service
public class MangaService {
    private final MangaRepository mangaRepository;
    public MangaService(MangaRepository mangaRepository)
    {
        this.mangaRepository=mangaRepository;
    }
    public Double getRating(String name)
    {
        Double Rating = mangaRepository.getRatingForName(name);
        if (Rating==null)
        {
            throw new IllegalArgumentException();
        }
        return Rating;
    }
}
