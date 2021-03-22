package com.example.books.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @ManyToMany(mappedBy = "books")
    private List<Author> authors = new ArrayList<>();

    @OneToMany(mappedBy = "bookId")
    private List<Edition> editions = new ArrayList<>();

    @ManyToMany(mappedBy = "books")
    private List<Translator> translators = new ArrayList<>();

    @Column(name = "isbn",
            unique = true)
    private Long isbn;

    @OneToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUserId;

    public Book() {
    }

    public Book(String title, String subtitle, Long isbn, AppUser appUserId) {
        this.title = title;
        this.subtitle = subtitle;
        this.isbn = isbn;
        this.appUserId = appUserId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Edition> getEditions() {
        return editions;
    }

    public void setEditions(List<Edition> editions) {
        this.editions = editions;
    }

    public List<Translator> getTranslators() {
        return translators;
    }

    public void setTranslators(List<Translator> translators) {
        this.translators = translators;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public AppUser getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(AppUser appUserId) {
        this.appUserId = appUserId;
    }

}
