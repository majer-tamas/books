package com.example.books.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "edition")
public class Edition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long editionId;

    @Column(name = "edition")
    private Integer edition;

    @Column(name = "year")
    private Integer year;

    @ManyToMany
    @JoinTable(name = "edition_publisher",
               joinColumns = { @JoinColumn(name = "fk_edition") },
               inverseJoinColumns = { @JoinColumn(name = "fk_publisher") })
    private Set<Publisher> publishers = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "edition_city",
               joinColumns = { @JoinColumn(name = "fk_edition") },
               inverseJoinColumns = { @JoinColumn(name = "fk_city") })
    private Set<City> cities = new HashSet<>();

    @OneToMany(mappedBy = "editionId")
    private List<CoverImage> coverImages = new ArrayList<>();

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "cover")
    private String cover;

    @Lob
    @Column(name = "blurb")
    private String blurb;

    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book bookId;

    public Edition() {
    }

    public Edition(Integer edition, Integer year, Integer pages, String cover, String blurb, Book bookId) {
        this.edition = edition;
        this.year = year;
        this.pages = pages;
        this.cover = cover;
        this.blurb = blurb;
        this.bookId = bookId;
    }

    public Long getEditionId() {
        return editionId;
    }

    public void setEditionId(Long editionId) {
        this.editionId = editionId;
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Set<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(Set<Publisher> publishers) {
        this.publishers = publishers;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    public List<CoverImage> getCoverImages() {
        return coverImages;
    }

    public void setCoverImages(List<CoverImage> coverImages) {
        this.coverImages = coverImages;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

}
