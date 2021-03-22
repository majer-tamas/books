package com.example.books.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @JsonIgnore
    @ManyToMany(mappedBy = "translators")
    private Set<Book> books = new HashSet<>();

    public Translator() {
    }

    public Translator(String fullName) {
        this.fullName = fullName;
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.getTranslators().add(this);
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

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

}
