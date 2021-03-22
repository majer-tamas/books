package com.example.books.repository;

import com.example.books.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUsername(String username);

    AppUser findByVerificationCode(String verificationCode);

    AppUser findByEmail(String email);

}
