import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {BookModel} from '../model/book.model';

const BASE_URL = 'http://localhost:8080/api/books';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private http:HttpClient) { }

  fetchAllBooks() : Observable<BookModel[]> {
    return this.http.get<BookModel[]>(BASE_URL + '/list');
  }

}
