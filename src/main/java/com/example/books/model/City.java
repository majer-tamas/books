package com.example.books.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;

    @Column(name = "name",
            unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "city_edition",
            joinColumns = { @JoinColumn(name = "city_id") },
            inverseJoinColumns = { @JoinColumn(name = "edition_id") }
    )
    private List<Edition> editions = new ArrayList<>();

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
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
