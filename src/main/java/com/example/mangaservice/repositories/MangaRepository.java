package com.example.mangaservice.repositories;
import com.example.mangaservice.entities.Manga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository


    public interface MangaRepository extends JpaRepository<Manga,Long> {
    Manga findByName(String name);
    @Query(value = "SELECT b.* FROM Manga b JOIN manga r ON b.id=r.id WHERE r.rating>:minRating", nativeQuery = true)
    List<Manga> findMangaByRating(@Param("minRating") Double minRating);
    }

