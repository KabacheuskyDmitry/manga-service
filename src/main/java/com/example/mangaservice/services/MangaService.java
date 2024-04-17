package com.example.mangaservice.services;
import com.example.mangaservice.exceptions.ListIsEmpty;
import com.example.mangaservice.dto.MangaDTO;
import com.example.mangaservice.entities.Manga;
import com.example.mangaservice.repositories.MangaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MangaService
{
    private final MangaRepository mangaRepository;
    public MangaService(MangaRepository mangaRepository)
    {
        this.mangaRepository=mangaRepository;
    }

    public List<MangaDTO> findAllBooks() {

        List<Manga> mangaList = mangaRepository.findAll();
        if (mangaList.isEmpty()) {
            throw new ListIsEmpty("List is Empty");
        }
        return mangaList.stream()
                .map(manga -> new MangaDTO(manga.getId(), manga.getName(), manga.getRating(), manga.getAuthor()))
                .toList();
    }
    public List<MangaDTO> getManga(String name)
    {
        List<Manga> mangaList = (List<Manga>)mangaRepository.findByName(name);
        if(mangaList.isEmpty())
        {
            throw new ListIsEmpty("List is Empty");
        }
        return mangaList.stream()
                .map(manga -> new MangaDTO(manga.getId(),manga.getName(), manga.getRating(), manga.getAuthor()))
                .toList();
    }
    public void saveManga(MangaDTO mangaDTO) {
        Manga manga = new Manga();
        manga.setName(mangaDTO.getName());
        manga.setAuthor(mangaDTO.getAuthor());
        manga.setRating(mangaDTO.getRating());
        mangaRepository.save(manga);
    }

    public MangaDTO updateManga(Long id,String name, Double rating, String author) {
        Manga manga = mangaRepository.findById(id)
                .orElseThrow(() -> new ListIsEmpty("Manga not found with id: " + id));

        if (name != null) {
            manga.setName(name);
        }
        if (rating != null) {
            manga.setRating(rating);
        }
        if (author != null) {
            manga.setAuthor(author);
        }

        mangaRepository.save(manga);
        return convertToDTO(manga);
    }
    public MangaDTO convertToDTO(Manga manga)
    {
        return new MangaDTO
                (manga.getId(),manga.getName(),manga.getRating(),manga.getAuthor());

    }

    public void deleteManga(Long id) {
        if(mangaRepository.existsById(id)){
            mangaRepository.deleteById(id);
        }
        else{
            throw  new EntityNotFoundException("Manga with ID [" + id + "] is not found.");
        }
    }
}
