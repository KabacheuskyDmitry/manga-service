package com.example.mangaservice.repositories;
import com.example.mangaservice.entities.Manga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository


    public interface MangaRepository extends JpaRepository<Manga,Long> {
    List<Manga> findByName(String name);
    }

