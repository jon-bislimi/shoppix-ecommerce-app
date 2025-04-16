package org.bisha.ecommercefinal.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.bisha.ecommercefinal.dtos.ReviewDto;
import org.bisha.ecommercefinal.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Validated
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<ReviewDto> addReview(@PathVariable @Min(1) Long productId,
                                               @Valid @RequestBody ReviewDto reviewDto) {
        ReviewDto createdReview = reviewService.addReview(productId, reviewDto);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByProductId(@PathVariable @Min(1) Long productId) {
        List<ReviewDto> reviews = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable @Min(1) Long reviewId,
                                                  @Valid @RequestBody ReviewDto reviewDto) {
        ReviewDto updatedReview = reviewService.updateReview(reviewId, reviewDto);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> deleteReview(@PathVariable @Min(1) Long reviewId) {
        ReviewDto deletedReview = reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(deletedReview);
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable @Min(1) Long reviewId) {
        ReviewDto review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByUserId(@PathVariable @Min(1) Long userId) {
        List<ReviewDto> reviews = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/average-rating/{productId}")
    public ResponseEntity<Double> getAverageRatingForProduct(@PathVariable @Min(1) Long productId) {
        double averageRating = reviewService.getAverageRatingForProduct(productId);
        return ResponseEntity.ok(averageRating);
    }

    @GetMapping("/user/{userId}/product/{productId}")
    public ResponseEntity<ReviewDto> getReviewByUserIdAndProductId(@PathVariable @Min(1) Long userId,
                                                                   @PathVariable @Min(1) Long productId) {
        ReviewDto review = reviewService.getReviewsByUserIdAndProductId(userId, productId);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/get-dto")
    public ResponseEntity<ReviewDto> getDto() {
        return ResponseEntity.ok(new ReviewDto());
    }
}
