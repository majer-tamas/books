package com.example.books.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username",
            unique = true,
            columnDefinition = "varbinary(255)")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email",
            unique = true,
            columnDefinition = "varbinary(255)")
    private String email;

    @Column(name = "verified")
    private Boolean verified = false;

    @Column(name = "banned")
    private Boolean banned = false;

    @Column(name = "verification_code",
            columnDefinition = "varchar(64)")
    private String verificationCode;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class)
    @JoinTable(name = "roles")
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "appUser")
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "appUser")
    private Set<Book> books = new HashSet<>();

    public AppUser() {
    }

    public AppUser(String username, String password, String email, String verificationCode) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.verificationCode = verificationCode;
        this.roles.add(Role.ROLE_USER);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

}
