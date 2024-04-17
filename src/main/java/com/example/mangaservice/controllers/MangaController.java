package com.example.mangaservice.controllers;

import com.example.mangaservice.dto.MangaDTO;
import com.example.mangaservice.entities.Manga;
import com.example.mangaservice.services.MangaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MangaController {
    private final MangaService mangaService;
    public MangaController(MangaService mangaService)
    {
        this.mangaService=mangaService;
    }

    @GetMapping("/manga")
    public List<MangaDTO> getManga(@RequestParam(required = false) String name)
    {

        return mangaService.getManga(name);
    }
    @PostMapping("/saveManga")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBook(@RequestBody MangaDTO data) {
        mangaService.saveManga(data);
    }

    @PutMapping("/updateManga/{id}")
    public ResponseEntity<MangaDTO> updateBook(@PathVariable Long id,
                                                  @RequestParam(required = false) String name,
                                                  @RequestParam(required = false) Double rating,
                                                  @RequestParam(required = false) String author) {
        MangaDTO updatedManga = mangaService.updateManga(id, name, rating, author);
        if (updatedManga != null) {
            return ResponseEntity.ok(updatedManga);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteManga/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            mangaService.deleteManga(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
