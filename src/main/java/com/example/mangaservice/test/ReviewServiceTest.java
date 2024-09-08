package com.example.mangaservice.test;

import com.example.mangaservice.entities.Manga;
import com.example.mangaservice.entities.Review;
import com.example.mangaservice.exceptions.ListIsEmpty;
import com.example.mangaservice.repositories.MangaRepository;
import com.example.mangaservice.repositories.ReviewRepository;
import com.example.mangaservice.services.ReviewService;
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
    private MangaRepository mangaRepository;

    @Mock
    private ReviewRepository reviewsRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    public Manga mangaConstructor(String title, String author, Double rating, long id) {
        Manga manga = new Manga();
        manga.setName(title);
        manga.setAuthor(author);
        manga.setId(id);
        manga.setRating(rating);
        manga.setReviews(new ArrayList<>());
        return manga;
    }
    public Review reviewConstructor(Manga manga,String content,long id)
    {
        Review review = new Review();
        review.setContent(content);
        review.setId(id);
        review.setManga(manga);
        return  review;
    }

    @Test
    void testGetReviewsNotFound() {
        String title = "Nonexistent Title";
        when(mangaRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(ListIsEmpty.class, () -> reviewService.getReviews(title));
    }
    @Test
    void testSaveReview() {
        // Подготовка
        String name = "Test Name";
        String content = "Great Manga!";
        long mangaId = 123456789L; // Предполагаемый ID книги
        Manga manga = mangaConstructor(name, "Name", 4.23, mangaId);
        when(mangaRepository.findByName(name)).thenReturn(manga);

        mangaRepository.save(manga);
        reviewService.saveReview(name, content);

        // Проверка
        verify(mangaRepository, times(1)).findByName(name);
        verify(reviewsRepository, times(1)).save(any(Review.class));
        assertTrue(reviewService.getReviews(name).stream().anyMatch(review -> review.getContent().equals(content)));
    }

    @Test
    void testUpdateReviewContentExistingReview() {
        Long existingReviewId = 1L;
        String newContent = "Updated Content";
        Review existingReview = new Review();
        existingReview.setId(existingReviewId);
        existingReview.setContent("Initial Content");

        when(reviewsRepository.findById(existingReviewId)).thenReturn(Optional.of(existingReview));

        reviewService.updateReviewContent(existingReviewId, newContent);

        verify(reviewsRepository, times(1)).findById(existingReviewId);
        verify(reviewsRepository, times(1)).save(any(Review.class));

        Review updatedReview = reviewsRepository.findById(existingReviewId).get();
        assertEquals(newContent, updatedReview.getContent());
    }

    @Test
    void testUpdateReviewContentNonExistingReview() {
        Long nonExistingReviewId = 999L;

        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.updateReviewContent(nonExistingReviewId, "New Content");
        });
    }
}