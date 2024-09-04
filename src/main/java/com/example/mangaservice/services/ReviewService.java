package com.example.mangaservice.services;

import com.example.mangaservice.dto.ReviewDTO;
import com.example.mangaservice.entities.Manga;
import com.example.mangaservice.entities.Review;
import com.example.mangaservice.exceptions.ListIsEmpty;
import com.example.mangaservice.repositories.MangaRepository;
import com.example.mangaservice.repositories.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final MangaRepository mangaRepository;
    private final ReviewRepository reviewRepository;

    public ReviewService(MangaRepository mangaRepository, ReviewRepository reviewRepository)
    {
        this.mangaRepository = mangaRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewDTO> getReviews(String name) {
        List<Manga> mangaList = mangaRepository.findAll();
        Manga foundManga = mangaList.stream()
                .filter(manga -> name.equals(manga.getName()))
                .findFirst()
                .orElseThrow(() -> new ListIsEmpty("Manga is not found"));
        List<ReviewDTO> reviews =foundManga.getReviews().stream()
                .map(review -> new ReviewDTO(review.getContent()))
                .toList();
        if(reviews.isEmpty())
        {
            throw new ListIsEmpty("No reviews");
        }
        return reviews;
    }
    public void saveReview(String name,String content)
    {
        Review review = new Review();
        review.setContent(content);
        Manga manga = mangaRepository.findByName(name);
        if(manga!=null) {
            review.setManga(manga);
        }
        else
        {
            throw new ListIsEmpty("Manga is not found");
        }

        reviewRepository.save(review);
    }


    public void deleteReview(Long id) {
        if(reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
        }
        else {
            throw  new EntityNotFoundException("Review with ID [" + id + "] is not found.");
        }
    }

    public void updateReviewContent(Long id, String content) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            review.setContent(content);
            reviewRepository.save(review);
        } else {
            throw new IllegalArgumentException("Review with ID [" + id + "] is not found.");
        }
    }

}