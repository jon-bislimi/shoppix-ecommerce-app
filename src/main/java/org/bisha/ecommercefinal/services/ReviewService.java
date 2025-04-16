package org.bisha.ecommercefinal.services;

import org.bisha.ecommercefinal.dtos.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto addReview(Long productId, ReviewDto reviewDto);

    List<ReviewDto> getReviewsByProductId(Long productId);

    ReviewDto updateReview(Long reviewId, ReviewDto reviewDto);

    ReviewDto deleteReview(Long reviewId);

    ReviewDto getReviewById(Long reviewId);

    List<ReviewDto> getReviewsByUserId(Long userId);

    double getAverageRatingForProduct(Long productId);

    ReviewDto getReviewsByUserIdAndProductId(Long userId, Long productId);
}
