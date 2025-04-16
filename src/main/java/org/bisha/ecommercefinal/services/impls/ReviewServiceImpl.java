package org.bisha.ecommercefinal.services.impls;

import org.bisha.ecommercefinal.dtos.ReviewDto;
import org.bisha.ecommercefinal.exceptions.ResourceAlreadyExistsException;
import org.bisha.ecommercefinal.exceptions.ResourceNotFoundException;
import org.bisha.ecommercefinal.mappers.ReviewMapper;
import org.bisha.ecommercefinal.repositories.ProductRepository;
import org.bisha.ecommercefinal.repositories.ReviewRepository;
import org.bisha.ecommercefinal.repositories.UserRepository;
import org.bisha.ecommercefinal.services.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper, ProductRepository productRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReviewDto addReview(Long productId, ReviewDto reviewDto) {
        if (reviewRepository.findById(reviewDto.getId()).isPresent()) {
            throw new ResourceAlreadyExistsException("Review already exists");
        }
//        if (productRepository.findById(productId).isEmpty()) {
//            throw new ResourceNotFoundException("Product not found");
//        }
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        var review = reviewMapper.toEntity(reviewDto);
        review.setProduct(product);
        product.getReviews().add(review);
        return reviewMapper.toDto(reviewRepository.save(review));
    }

    @Override
    public List<ReviewDto> getReviewsByProductId(Long productId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return reviewMapper.toDtoList(reviewRepository.findByProduct(product));
    }

    @Override
    public ReviewDto updateReview(Long reviewId, ReviewDto reviewDto) {
        var review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        if (reviewRepository.findById(reviewId).isEmpty()) {
            throw new ResourceNotFoundException("Review not found");
        }
        review.setRating(reviewDto.getRating());
        review.setDescription(reviewDto.getDescription());
        return reviewMapper.toDto(reviewRepository.save(review));
    }

    @Override
    public ReviewDto deleteReview(Long reviewId) {
        var review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        if (reviewRepository.findById(reviewId).isEmpty()) {
            throw new ResourceNotFoundException("Review not found");
        }
        reviewRepository.delete(review);
        return reviewMapper.toDto(review);
    }

    @Override
    public ReviewDto getReviewById(Long reviewId) {
        return reviewMapper.toDto(reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found")));
    }

    @Override
    public List<ReviewDto> getReviewsByUserId(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return reviewMapper.toDtoList(reviewRepository.findByUser(user));
    }

    @Override
    public double getAverageRatingForProduct(Long productId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return reviewRepository.findByProduct(product).stream()
                .mapToDouble(review -> reviewMapper.toDto(review).getRating())
                .average()
                .orElse(0.0);
    }

    @Override
    public ReviewDto getReviewsByUserIdAndProductId(Long userId, Long productId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return reviewMapper.toDto(reviewRepository.findByUserAndProduct(user, product).get());
    }

}
