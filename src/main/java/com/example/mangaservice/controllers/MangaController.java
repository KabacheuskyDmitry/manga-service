package com.example.mangaservice.controllers;

import com.example.mangaservice.components.RequestCounter;
import com.example.mangaservice.dto.MangaDTO;
import com.example.mangaservice.entities.Manga;
import com.example.mangaservice.services.MangaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.RequestEntity.post;
@Tag(name = "Manga Controller", description = "Operations related to manga")
@RestController
public class MangaController {
    private final MangaService mangaService;
    private final RequestCounter requestCounter;

    public MangaController(MangaService mangaService, RequestCounter requestCounter) {
        this.mangaService = mangaService;
        this.requestCounter = requestCounter;
    }
    @GetMapping("/manga")
    @Operation(summary = "Get manga by name", description = "Returns a list of manga matching the given name")
    public List<MangaDTO> getManga(@RequestParam(required = false) String name)
    {
        requestCounter.incrementAndGet();
        return mangaService.getManga(name);
    }
    @GetMapping("/manga/height-rating")
    @Operation(summary = "find manga with height rating", description = "Shows mangas with rating higher then minRating")
    public List<MangaDTO> getMangaWithHeightRating(@RequestParam Double minRating)
    {
        requestCounter.incrementAndGet();
        return mangaService.findMangaByRating(minRating);
    }

    @PostMapping("/saveMangas")
    @Operation(summary = "Save multiple mangas", description = "Creates multiple manga entries")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveBooks(@RequestBody List<MangaDTO> mangaDTOs) {
        requestCounter.incrementAndGet();
        mangaService.saveMangas(mangaDTOs);
    }
    @PostMapping("/saveManga")
    @ResponseStatus(HttpStatus.CREATED)
    public void createManga(@RequestBody MangaDTO data) {
        requestCounter.incrementAndGet();
        mangaService.saveManga(data);
    }


    @PutMapping("/updateManga/{id}")
    public ResponseEntity<MangaDTO> updateManga(@PathVariable Long id,
                                                @RequestParam(required = false) String name,
                                                @RequestParam(required = false) Double rating,
                                                @RequestParam(required = false) String author) {
        requestCounter.incrementAndGet();
        MangaDTO updatedManga = mangaService.updateManga(id, name, rating, author);
        if (updatedManga != null) {
            return ResponseEntity.ok(updatedManga);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteManga/{id}")
    public ResponseEntity<Void> deleteManga(@PathVariable Long id) {
        requestCounter.incrementAndGet();
        try {
            mangaService.deleteManga(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
