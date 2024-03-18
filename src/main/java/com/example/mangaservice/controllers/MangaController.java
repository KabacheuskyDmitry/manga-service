package com.example.mangaservice.controllers;

import com.example.mangaservice.services.MangaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/manga")
public class MangaController {
    private final MangaService mangaService;
    public MangaController(MangaService mangaService)
    {
        this.mangaService=mangaService;
    }

    @GetMapping("/{name}")
    public Double getRating(@PathVariable String name)
    {
        return mangaService.getRating(name);
    }
}
