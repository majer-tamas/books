import { Component, OnInit } from '@angular/core';
import {BookService} from '../../service/book.service';
import {BookModel} from '../../model/book.model';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

  books: BookModel[] = [];
  constructor(private bookService:BookService) { }

  ngOnInit(): void {
    this.bookService.fetchAllBooks().subscribe(
      (response) => {
        this.books = response;
      }, error => console.warn(error)
    )
  }

}
