package com.example.books.dto;

import com.example.books.model.Review;

public class ReviewResponseDTO {

    private String author;
    private Integer point;
    private String text;

    public ReviewResponseDTO(Review review) {
        this.author = review.getAppUser().getUsername();
        this.point = review.getPoint();
        this.text = review.getReviewText();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
