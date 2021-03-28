package com.example.books.dto;

import com.example.books.model.Book;

public class BookResponseDTO {

    private String title;

    public BookResponseDTO(Book book) {
        this.title = book.getTitle();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
