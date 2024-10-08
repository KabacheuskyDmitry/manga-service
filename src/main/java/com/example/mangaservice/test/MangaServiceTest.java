package com.example.mangaservice.test;

import com.example.mangaservice.entities.Manga;
import com.example.mangaservice.exceptions.ListIsEmpty;
import com.example.mangaservice.services.MangaService;
import com.example.mangaservice.repositories.MangaRepository;
import com.example.mangaservice.cache.Cache;
import com.example.mangaservice.dto.MangaDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MangaServiceTest {

    private MangaService mangasService;
    private MangaRepository mangasRepository;
    private Cache myCache;

    private String name1 = "Manga 1";
    private String name2 = "Manga 2";
    private String author1 = "Author 1";
    private String author2 = "Author 1";

    @BeforeEach
    public void setUp() {
        mangasRepository = mock(MangaRepository.class);
        myCache = mock(Cache.class);
        mangasService = new MangaService(mangasRepository, myCache);
    }
    public Manga mangaConstructor(String name, String author, double rating, long id) {
        Manga manga = new Manga();
        manga.setName(name);
        manga.setAuthor(author);
        manga.setId(id);
        manga.setRating(rating);
        return manga;
    }
    @Test
    public void testFindAllMangas() {

        Manga manga1 = mangaConstructor(name1,author1,4.23,1L);

        Manga manga2 = mangaConstructor(name2, author2,4.03,2L);

        List<Manga> mangas = Arrays.asList(manga1, manga2);

        when(mangasRepository.findAll()).thenReturn(mangas);

        List<MangaDTO> result = mangasService.findAllManga();

        assertAll("Проверка всех книг",
                () -> assertEquals(2, result.size(), "Размер списка должен быть 2"),
                () -> assertEquals(name1, result.get(0).getName(), "Заголовок первой манги должен быть 'Manga 1'"),
                () -> assertEquals(4.23, result.get(0).getRating(), "Рейтинг первой манги должен быть '4,23'"),
                () -> assertEquals(author1, result.get(0).getAuthor(), "Автор первой манги должен быть 'Author 1'"),
                () -> assertEquals(name2, result.get(1).getName(), "Заголовок второй манги должен быть 'Manga 2'"),
                () -> assertEquals(4.03, result.get(1).getRating(), "Рейтинг второй манги должен быть '4,03'"),
                () -> assertEquals(author2, result.get(1).getAuthor(), "Автор второй манги должен быть 'Author 2'")
        );
    }
    @Test
    public void testSaveManga() {
        MangaDTO mangadto = new MangaDTO((long)0,name1, 4.23, author1);

        mangasService.saveManga(mangadto);

        verify(mangasRepository, times(1)).save(any(Manga.class));
    }

    @Test
    public void testUpdateManga() {
        Long id = 1L;
        String name = "New Name";
        Double rating = 4.23;
        String author = "New Author";

        Manga expectedManga = new Manga();
        expectedManga.setAuthor(author);
        expectedManga.setName(name);
        expectedManga.setRating(rating);
        expectedManga.setId(id);

        when(mangasRepository.findById(id)).thenReturn(Optional.of(expectedManga));

        MangaDTO actualDTO = mangasService.updateManga(id, name, rating, author);

        assertEquals(expectedManga.getName(), actualDTO.getName());
        assertEquals(expectedManga.getRating(), actualDTO.getRating());
        assertEquals(expectedManga.getAuthor(), actualDTO.getAuthor());

        verify(mangasRepository, times(1)).save(expectedManga);
    }
    @Test
    public void testConvertToDTO() {
        Manga manga = mangaConstructor(name1,"author",4.23,1L);

        MangaDTO dto = mangasService.convertToDTO(manga);

        assertEquals(manga.getId(), dto.getId());
        assertEquals(manga.getName(), dto.getName());
        assertEquals(manga.getRating(), dto.getRating());
        assertEquals(manga.getAuthor(), dto.getAuthor());
    }
    @Test
    public void testDeleteMangaExists() {
        Long id = 1L;

        when(mangasRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> mangasService.deleteManga(id));

        verify(mangasRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteMangaNotExists() {
        Long id = 1L;

        when(mangasRepository.existsById(id)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> mangasService.deleteManga(id));
        assertEquals("Книга с ID: [" + id + "] не найдена", exception.getMessage());

        verify(mangasRepository, never()).deleteById(id);
    }
    @Test
    public void findMangasWithHighRatingCachedMangasExist() {
        double minRating = 4;

        String cacheKey = "MangasWithHighRating_" + minRating;
        List<MangaDTO> cachedMangas = Collections.singletonList(new MangaDTO(1L, "Manga Name", 4.23, "Author"));

        when(myCache.get(cacheKey)).thenReturn(cachedMangas);

        List<MangaDTO> result = mangasService.findMangaByRating(minRating);

        assertEquals(cachedMangas, result);
        verify(myCache, times(1)).get(cacheKey);
        verify(mangasRepository, never()).findMangaByRating(minRating);
    }

    @Test
    void testFindMangasWithHighRatingEmptyList() {
        double minRating = 5;
        when(mangasRepository.findMangaByRating(minRating)).thenReturn(Collections.emptyList());

        assertThrows(ListIsEmpty.class, () -> mangasService.findMangaByRating(minRating));
    }
    @Test
    void findMangasWithHighRatingFromCache() {
        double minRating = 5;
        List<MangaDTO> expectedMangas = Arrays.asList(new MangaDTO(1L, name1, 4.23, author1),
                new MangaDTO(2L, name2, 4.03, author2));
        when(myCache.get(anyString())).thenReturn(expectedMangas);
        myCache.put("MangasWithHighRating_"+minRating,expectedMangas);
        List<MangaDTO> result = mangasService.findMangaByRating(minRating);

        verify(myCache, times(1)).get(anyString());
        assertEquals(expectedMangas, result);
    }
    @Test
    void testFindMangasWithHighRatingFromRepoAndCache() {
        double minRating = 5;
        List<Manga> mangas = Arrays.asList(mangaConstructor(name1, author1, 4.23, 1L), mangaConstructor(name2, author2, 4.03, 2L));
        List<MangaDTO> expectedMangas = mangas.stream()
                .map(manga -> new MangaDTO(manga.getId(), manga.getName(), manga.getRating(), manga.getAuthor()))
                .toList();
        when(mangasRepository.findMangaByRating(minRating)).thenReturn(mangas);
        when(myCache.get(anyString())).thenReturn(null);

        List<MangaDTO> result = mangasService.findMangaByRating(minRating);

        verify(myCache, times(1)).put(anyString(), anyList());
        verify(mangasRepository, times(1)).findMangaByRating(minRating);
        assertEquals(expectedMangas, result);
    }
    @Test// проверка что метод сэйв вызоветмя 2 раза для каждой книги
    void testSaveMangas() {
        List<MangaDTO> mangas = Arrays.asList(
                new MangaDTO(1L, name1, 4.23, author1),
                new MangaDTO(2L, name2, 4.03, author2)
        );
        mangasService.saveMangas(mangas);
        verify(mangasRepository, times(2)).save(any(Manga.class));
    }

}