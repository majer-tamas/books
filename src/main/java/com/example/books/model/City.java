package com.example.books.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;

    @Column(name = "name",
            unique = true)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "cities")
    private Set<Edition> editions = new HashSet<>();

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    public void addEdition(Edition edition) {
        this.editions.add(edition);
        edition.getCities().add(this);
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

    public Set<Edition> getEditions() {
        return editions;
    }

    public void setEditions(Set<Edition> editions) {
        this.editions = editions;
    }

}
