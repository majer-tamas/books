import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {BookModel} from '../model/book.model';
import {BookFormModel} from '../model/bookForm.model';

const BASE_URL = 'http://localhost:8080/api/books';

@Injectable({
              providedIn: 'root',
            })
export class BookService {

  constructor(private http: HttpClient) {
  }

  fetchAllBooks(): Observable<BookModel[]> {
    return this.http.get<BookModel[]>(BASE_URL + '/list');
  }

  fetchBookById(id: number): Observable<any> {
    return this.http.get(BASE_URL + '/find-book/' + id);
  }

  createNewBook(data: BookFormModel): Observable<any> {
    return this.http.post<any>(BASE_URL + '/create', data);
  }

}
