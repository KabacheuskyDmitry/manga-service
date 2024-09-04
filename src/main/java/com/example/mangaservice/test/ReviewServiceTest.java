package com.example.mangaservice.test;

import com.example.mangaservice.dto.ReviewDTO;
import com.example.mangaservice.entities.Manga;
import com.example.mangaservice.entities.Review;
import com.example.mangaservice.exceptions.ListIsEmpty;
import com.example.mangaservice.repositories.MangaRepository;
import com.example.mangaservice.repositories.ReviewRepository;
import com.example.mangaservice.services.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private MangaRepository MangaRepository;

    @Mock
    private ReviewRepository reviewsRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    public Manga MangaConstructor(String title, String author, Double rating,long id) {
        Manga Manga = new Manga();
        Manga.setName(title);
        Manga.setAuthor(author);
        Manga.setId(id);
        Manga.setRating(rating);
        Manga.setReviews(new ArrayList<>());
        return Manga;
    }
    public Review reviewConstructor(Manga Manga,String content,long id)
    {
        Review review = new Review();
        review.setContent(content);
        review.setId(id);
        review.setManga(Manga);
        return  review;
    }

    @Test
    void testGetReviewsNotFound() {
        String title = "Nonexistent Title";
        when(MangaRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(ListIsEmpty.class, () -> reviewService.getReviews(title));
    }
    @Test
    void testSaveReview() {
        // Подготовка
        String name = "Test Name";
        String content = "Great Manga!";
        long mangaId = 123456789L; // Предполагаемый ID книги
        long reviewId = 987654321L; // Предполагаемый ID отзыва
        Manga Manga = MangaConstructor(name, "Name", 4.23, mangaId);
        // Нас тройка поведения моков
        when(MangaRepository.findByName(name)).thenReturn(Manga);

        MangaRepository.save(Manga);
        reviewService.saveReview(name, content);

        // Проверка
        verify(MangaRepository, times(1)).findByName(name);
        verify(reviewsRepository, times(1)).save(any(Review.class));
        assertTrue(reviewService.getReviews(name).stream().anyMatch(review -> review.getContent().equals(content)));
    }

    @Test
    void testUpdateReviewContent_ExistingReview() {
        // Подготовка
        Long existingReviewId = 1L;
        String newContent = "Updated Content";
        Review existingReview = new Review();
        existingReview.setId(existingReviewId);
        existingReview.setContent("Initial Content");

        // Настройка мока для симуляции существующего отзыва
        when(reviewsRepository.findById(existingReviewId)).thenReturn(Optional.of(existingReview));

        // Выполнение
        reviewService.updateReviewContent(existingReviewId, newContent);

        // Проверка
        verify(reviewsRepository, times(1)).findById(existingReviewId);
        verify(reviewsRepository, times(1)).save(any(Review.class));

        // Проверка, что содержание отзыва было обновлено
        Review updatedReview = reviewsRepository.findById(existingReviewId).get();
        assertEquals(newContent, updatedReview.getContent());
    }

    @Test
    void testUpdateReviewContent_NonExistingReview() {
        // Подготовка
        Long nonExistingReviewId = 999L;

        // Ожидаемое исключение
        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.updateReviewContent(nonExistingReviewId, "New Content");
        });
    }
}