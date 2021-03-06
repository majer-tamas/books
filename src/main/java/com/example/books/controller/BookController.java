package com.example.books.controller;

import com.example.books.dto.BookDTO;
import com.example.books.dto.BookResponseDTO;
import com.example.books.dto.ReviewDTO;
import com.example.books.dto.ReviewResponseDTO;
import com.example.books.model.Book;
import com.example.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewBook(@RequestBody BookDTO bookDTO) {
        Book book = bookService.createNewBook(bookDTO);
        return ResponseEntity.ok(book.getId());
    }

    @GetMapping("find-book/{id}")
    public ResponseEntity<?> findBookById(@PathVariable Long id) {
        Book book = bookService.findBookById(id);
        BookResponseDTO bookResponseDTO = new BookResponseDTO(book);
        return ResponseEntity.ok(bookResponseDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BookResponseDTO>> fetchAllBooks() {
        List<Book> books = bookService.fetchAllBooks();
        List<BookResponseDTO> bookResponseDTOS = books.stream().map(BookResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(bookResponseDTOS);
    }

    @PostMapping("/add-review")
    public ResponseEntity<?> addReview(@RequestBody ReviewDTO reviewDTO) {
        bookService.addReview(reviewDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("reviews-by-book/{id}")
    public ResponseEntity<?> fetchReviewsByBookId(@PathVariable Long id) {
        List<ReviewResponseDTO> reviews = bookService.fetchReviewsByBookId(id);
        return ResponseEntity.ok(reviews);
    }

}
