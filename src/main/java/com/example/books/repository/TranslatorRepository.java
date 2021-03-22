package com.example.books.repository;

import com.example.books.model.Translator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslatorRepository extends JpaRepository<Translator, Long> {
    Translator findByFullName(String fullName);
}
