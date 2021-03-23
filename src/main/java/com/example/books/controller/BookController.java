package com.example.books.controller;

import com.example.books.dto.BookDTO;
import com.example.books.dto.ReviewDTO;
import com.example.books.model.Book;
import com.example.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewBook(@RequestBody BookDTO bookDTO) {
        bookService.createNewBook(bookDTO);
        return null;
    }

    @GetMapping("find-book/{id}")
    public ResponseEntity<?> findBookById(@PathVariable Long id) {
        Book book = bookService.findBookById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping("/add-review")
    public ResponseEntity<?> addReview(@RequestBody ReviewDTO reviewDTO) {
        bookService.addReview(reviewDTO);
        return ResponseEntity.ok().build();
    }

}
