package com.example.mangaservice.controllers;



import com.example.mangaservice.dto.ReviewDTO;
import com.example.mangaservice.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {
    private final ReviewService reviewService;
    public ReviewController( ReviewService reviewService)
    {
        this.reviewService = reviewService;
    }


    @GetMapping("/mangaReviews")
    public List<ReviewDTO> getReviews(@RequestParam String name) {
        return reviewService.getReviews(name);
    }

    @PostMapping("/postReview")
    @ResponseStatus(HttpStatus.CREATED)
    public void createReview(@RequestParam String name,String content)
    {
        reviewService.saveReview(name,content);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/UpdateReview/content/{id}")
    public ResponseEntity<String> updateReviewContent(@PathVariable Long id, @RequestBody String content) {
        reviewService.updateReviewContent(id, content);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/UpdateReview/rating/{id}")
    public ResponseEntity<String> updateReviewRating(@PathVariable Long id, @RequestBody int rating) {
        reviewService.updateReviewRating(id);
        return ResponseEntity.ok().build();
    }
}