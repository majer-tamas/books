package com.example.books.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(name = "book_author",
               joinColumns = { @JoinColumn(name = "fk_book") },
               inverseJoinColumns = { @JoinColumn(name = "fk_author") })
    private Set<Author> authors = new HashSet<>();

    @OneToMany(mappedBy = "bookId")
    private Set<Edition> editions = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "book_translator",
               joinColumns = { @JoinColumn(name = "fk_book") },
               inverseJoinColumns = { @JoinColumn(name = "fk_translator") })
    private Set<Translator> translators = new HashSet<>();

    @Column(name = "isbn",
            unique = true)
    private String isbn;

    @OneToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUserId;

    public Book() {
    }

    public Book(String title, String subtitle, String isbn, AppUser appUserId) {
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

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Edition> getEditions() {
        return editions;
    }

    public void setEditions(Set<Edition> editions) {
        this.editions = editions;
    }

    public Set<Translator> getTranslators() {
        return translators;
    }

    public void setTranslators(Set<Translator> translators) {
        this.translators = translators;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public AppUser getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(AppUser appUserId) {
        this.appUserId = appUserId;
    }

}
