package com.example.books.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "translator")
public class Translator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name",
            unique = true)
    private String fullName;

    @ManyToMany
    @JoinTable(
            name = "book_translator",
            joinColumns = { @JoinColumn(name = "translator_id") },
            inverseJoinColumns = { @JoinColumn(name = "book_id") }
    )
    private List<Book> books = new ArrayList<>();

    public Translator() {
    }

    public Translator(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
