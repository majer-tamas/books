import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {BookService} from '../../service/book.service';

@Component({
             selector   : 'app-book-details',
             templateUrl: './book-details.component.html',
             styleUrls  : ['./book-details.component.css'],
           })
export class BookDetailsComponent implements OnInit {

  id: number = 0;
  title: string | null = null;

  constructor(private activatedRoute: ActivatedRoute,
              private bookService: BookService) {

  }

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params['id'];
    this.bookService.fetchBookById(this.id).subscribe(
      (response) => {
        console.log(response);
      }, error => console.warn(error),
    );
  }

}
