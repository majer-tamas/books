package com.example.books.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "review_text")
    private String reviewText;

    @Column(name = "point")
    private Integer point;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "book")
    private Book book;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "app_user")
    private AppUser appUser;

    public Review() {
    }

    public Review(String reviewText, Integer point, Book book, AppUser appUser) {
        this.reviewText = reviewText;
        this.point = point;
        this.book = book;
        this.appUser = appUser;
    }

    public void addBook(Book book) {
        this.book = book;
        book.getReviews().add(this);
    }

    public void addUser(AppUser appUser) {
        this.appUser = appUser;
        appUser.getReviews().add(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

}
