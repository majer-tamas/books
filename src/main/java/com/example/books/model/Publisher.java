package com.example.books.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "publisher")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long publisherId;

    @Column(name = "name",
            unique = true)
    private String name;

    @ManyToMany(mappedBy = "publishers")
    private Set<Edition> editions = new HashSet<>();

    public Publisher() {
    }

    public Publisher(String name) {
        this.name = name;
    }

    public void addEdition(Edition edition) {
        this.editions.add(edition);
        edition.getPublishers().add(this);
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Edition> getEditions() {
        return editions;
    }

    public void setEditions(Set<Edition> editions) {
        this.editions = editions;
    }

}
