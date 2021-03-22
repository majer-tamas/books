package com.example.books.model;

import javax.persistence.*;

@Entity
@Table(name = "cover_image")
public class CoverImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "edition_id")
    private Edition editionId;

    public CoverImage() {
    }

    public CoverImage(Edition editionId) {
        this.editionId = editionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Edition getEditionId() {
        return editionId;
    }

    public void setEditionId(Edition editionId) {
        this.editionId = editionId;
    }

}
