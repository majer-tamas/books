package com.example.books.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publisher")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long publisherId;

    @Column(name = "name",
            unique = true)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "publisher_edition",
            joinColumns = { @JoinColumn(name = "publisher_id") },
            inverseJoinColumns = { @JoinColumn(name = "edition_id") }
    )
    private List<Edition> editions = new ArrayList<>();

    public Publisher() {
    }

    public Publisher(String name) {
        this.name = name;
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

    public List<Edition> getEditions() {
        return editions;
    }

    public void setEditions(List<Edition> editions) {
        this.editions = editions;
    }

}
