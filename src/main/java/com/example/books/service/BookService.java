package com.example.books.service;

import com.example.books.dto.BookDTO;
import com.example.books.dto.ReviewDTO;
import com.example.books.dto.ReviewResponseDTO;
import com.example.books.model.*;
import com.example.books.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final AppUserRepository appUserRepository;
    private final CityRepository cityRepository;
    private final PublisherRepository publisherRepository;
    private final TranslatorRepository translatorRepository;
    private final AuthorRepository authorRepository;
    private final EditionRepository editionRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public BookService(BookRepository bookRepository,
                       AppUserRepository appUserRepository,
                       CityRepository cityRepository,
                       PublisherRepository publisherRepository,
                       TranslatorRepository translatorRepository,
                       AuthorRepository authorRepository,
                       EditionRepository editionRepository,
                       ReviewRepository reviewRepository
    ) {
        this.bookRepository = bookRepository;
        this.appUserRepository = appUserRepository;
        this.cityRepository = cityRepository;
        this.publisherRepository = publisherRepository;
        this.translatorRepository = translatorRepository;
        this.authorRepository = authorRepository;
        this.editionRepository = editionRepository;
        this.reviewRepository = reviewRepository;
    }

    public Book findBookById(Long id) {
        return bookRepository.findBookById(id);
    }

    public void createNewBook(BookDTO bookDTO) {
        AppUser user = appUserRepository.findByUsername(bookDTO.getUsername());

        Book book = bookRepository.save(new Book(bookDTO.getTitle(), bookDTO.getSubtitle(), bookDTO.getIsbn(), user));
        handleAuthors(bookDTO.getAuthors(), book);
        handleTranslators(bookDTO.getTranslators(), book);

        Edition edition = editionRepository.save(new Edition(bookDTO.getEdition(), bookDTO.getYear(), bookDTO.getPages(), bookDTO.getCover(), bookDTO.getBlurb(), book));
        handleCities(bookDTO.getCities(), edition);
        handlePublishers(bookDTO.getPublishers(), edition);
    }

    public List<Book> fetchAllBooks() {
        return bookRepository.findAll();
    }

    private void handleTranslators(List<String> translatorsFromDTO, Book book) {
        for (String translator : translatorsFromDTO) {
            if (translatorRepository.findByFullName(translator) == null) {
                translatorRepository.save(new Translator(translator)).addBook(book);
            } else {
                translatorRepository.findByFullName(translator).addBook(book);
            }
        }
    }

    private void handleAuthors(List<String> authorsFromDTO, Book book) {
        for (String author : authorsFromDTO) {
            if (authorRepository.findByFullName(author) == null) {
                authorRepository.save(new Author(author)).addBook(book);
            } else {
                authorRepository.findByFullName(author).addBook(book);
            }
        }
    }

    private void handleCities(List<String> citiesFromDTO, Edition edition) {
        for (String city : citiesFromDTO) {
            if (cityRepository.findByName(city) == null) {
                cityRepository.save(new City(city)).addEdition(edition);
            } else {
                cityRepository.findByName(city).addEdition(edition);
            }
        }
    }

    private void handlePublishers(List<String> publishersFromDTO, Edition edition) {
        for (String publisher : publishersFromDTO) {
            if (publisherRepository.findByName(publisher) == null) {
                publisherRepository.save(new Publisher(publisher)).addEdition(edition);
            } else {
                publisherRepository.findByName(publisher).addEdition(edition);
            }
        }
    }

    public void addReview(ReviewDTO reviewDTO) {
        String text = reviewDTO.getText();
        Integer point = reviewDTO.getPoint();
        Book book = bookRepository.findBookById(reviewDTO.getBookId());
        AppUser appUser = appUserRepository.findById(reviewDTO.getUserId()).get();
        Review savedReview = reviewRepository.save(new Review(text, point, book, appUser));
        savedReview.addBook(book);
        savedReview.addUser(appUser);
    }

    public List<ReviewResponseDTO> fetchReviewsByBookId(Long id) {
        return reviewRepository.findReviewsByBook(bookRepository.findBookById(id)).stream().map(ReviewResponseDTO::new).collect(Collectors.toList());
    }

}
