package com.example.books.repository;

import com.example.books.model.Book;
import com.example.books.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.book = :book")
    List<Review> findReviewsByBook(@Param("book") Book book);

}
