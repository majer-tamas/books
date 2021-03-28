import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {BookService} from '../../service/book.service';
import {JWTTokenService} from '../../service/jwt-token.service';
import {Router} from '@angular/router';

@Component({
             selector   : 'app-book-form',
             templateUrl: './book-form.component.html',
             styleUrls  : ['./book-form.component.css'],
           })
export class BookFormComponent implements OnInit {

  form: FormGroup;
  currentYear: number = new Date().getFullYear();
  authors: string[] = [];
  translators: string[] = [];
  publishers: string[] = [];
  cities: string[] = [];

  titleInvalidMessage: string | null = '';
  subtitleInvalidMessage: string | null = '';
  authorsInvalidMessage: string | null = '';
  translatorsInvalidMessage: string | null = '';
  isbnInvalidMessage: string | null = '';
  editionInvalidMessage: string | null = '';
  yearInvalidMessage: string | null = '';
  publishersInvalidMessage: string | null = '';
  citiesInvalidMessage: string | null = '';
  pagesInvalidMessage: string | null = '';
  coverInvalidMessage: string | null = '';
  blurbInvalidMessage: string | null = '';

  constructor(private formBuilder: FormBuilder,
              private bookService: BookService,
              private jwtTokenService: JWTTokenService,
              private router:Router) {
    this.form = this.formBuilder.group({
                                         title      : [null],
                                         subtitle   : [null],
                                         authors    : [null],
                                         translators: [null],
                                         isbn       : [null],
                                         username   : jwtTokenService.getUsername(),
                                         edition    : [null],
                                         year       : [null],
                                         publishers : [null],
                                         cities     : [null],
                                         pages      : [null],
                                         cover      : [null],
                                         blurb      : [null],
                                       });
  }

  ngOnInit(): void {
  }

  submit() {
    this.handleAuthors(this.form.get('authors')?.value);
    this.handleTranslators(this.form.get('translators')?.value);
    this.handlePublishers(this.form.get('publishers')?.value);
    this.handleCities(this.form.get('cities')?.value);

    console.log(this.form.value);

    this.bookService.createNewBook(this.form.value).subscribe(
      (response) => {
        console.log(response)
        this.router.navigate([`/book-details/${response}`])
      }, error => console.warn(error),
    );
  }

  allInputValid() {
    return true;
  }

  private handleAuthors(authors: string) {
    this.authors = [];
    if (authors && authors.includes(';')) {
      let authorsArray: string[] = authors.split(';');
      authorsArray.forEach((author) => {
        this.authors.push(author);
      });
    } else if (authors) {
      this.authors.push(authors);
    }
    this.form.patchValue({authors: this.authors});
  }

  private handleTranslators(translators: string) {
    this.translators = [];
    if (translators && translators.includes(';')) {
      let translatorsArray: string[] = translators.split(';');
      translatorsArray.forEach((translator) => {
        this.translators.push(translator);
      });
    } else if (translators) {
      this.translators.push(translators);
    }
    this.form.patchValue({translators: this.translators});
  }

  private handlePublishers(publishers: string) {
    this.publishers = [];
    if (publishers && publishers.includes(';')) {
      let publishersArray: string[] = publishers.split(';');
      publishersArray.forEach((publisher) => {
        this.publishers.push(publisher);
      });
    } else if (publishers) {
      this.publishers.push(publishers);
    }
    this.form.patchValue({publishers: this.publishers});
  }

  private handleCities(cities: string) {
    this.cities = [];
    if (cities && cities.includes(';')) {
      let citiesArray: string[] = cities.split(';');
      citiesArray.forEach((city) => {
        this.cities.push(city);
      });
    } else if (cities) {
      this.cities.push(cities);
    }
    this.form.patchValue({cities: this.cities});
  }

}
