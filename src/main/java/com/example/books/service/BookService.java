package com.example.books.service;

import com.example.books.dto.BookDTO;
import com.example.books.model.*;
import com.example.books.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    public BookService(BookRepository bookRepository,
                       AppUserRepository appUserRepository,
                       CityRepository cityRepository,
                       PublisherRepository publisherRepository,
                       TranslatorRepository translatorRepository,
                       AuthorRepository authorRepository,
                       EditionRepository editionRepository
    ) {
        this.bookRepository = bookRepository;
        this.appUserRepository = appUserRepository;
        this.cityRepository = cityRepository;
        this.publisherRepository = publisherRepository;
        this.translatorRepository = translatorRepository;
        this.authorRepository = authorRepository;
        this.editionRepository = editionRepository;
    }

    public Book findBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        //TODO EXCEPTION
        return book.orElse(null);
    }

    public void createNewBook(BookDTO bookDTO) {
        AppUser user = appUserRepository.findByUsername(bookDTO.getUsername());

        Book book = bookRepository.save(new Book(bookDTO.getTitle(), bookDTO.getTitle(), bookDTO.getIsbn(), user));
        book.setTranslators(handleTranslators(bookDTO.getTranslators()));
        book.setAuthors(handleAuthors(bookDTO.getAuthors()));

        Edition edition = editionRepository.save(new Edition(bookDTO.getEdition(), bookDTO.getYear(), bookDTO.getPages(), bookDTO.getCover(), bookDTO.getBlurb(), book));
        edition.setCities(handleCities(bookDTO.getCities()));
        edition.setPublishers(handlePublishers(bookDTO.getPublishers()));
    }

    private List<Translator> handleTranslators(List<String> translatorsFromDTO) {
        List<Translator> translators = new ArrayList<>();
        for (String translator : translatorsFromDTO) {
            if (translatorRepository.findByFullName(translator) == null) {
                translators.add(translatorRepository.save(new Translator(translator)));
            } else {
                translators.add(translatorRepository.findByFullName(translator));
            }
        }
        return translators;
    }

    private List<Author> handleAuthors(List<String> authorsFromDTO) {
        List<Author> authors = new ArrayList<>();
        for (String author : authorsFromDTO) {
            if (authorRepository.findByFullName(author) == null) {
                authors.add(authorRepository.save(new Author(author)));
            } else {
                authors.add(authorRepository.findByFullName(author));
            }
        }
        return authors;
    }

    private List<City> handleCities(List<String> citiesFromDTO) {
        List<City> cities = new ArrayList<>();
        for (String city : citiesFromDTO) {
            if (cityRepository.findByName(city) == null) {
                cities.add(cityRepository.save(new City(city)));
            } else {
                cities.add(cityRepository.findByName(city));
            }
        }
        return cities;
    }

    private List<Publisher> handlePublishers(List<String> publishersFromDTO) {
        List<Publisher> publishers = new ArrayList<>();
        for (String publisher : publishersFromDTO) {
            if (publisherRepository.findByName(publisher) == null) {
                publishers.add(publisherRepository.save(new Publisher(publisher)));
            } else {
                publishers.add(publisherRepository.findByName(publisher));
            }
        }
        return publishers;
    }

}
