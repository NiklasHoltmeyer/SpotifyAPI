import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import {UserProfile} from '../../interface/UserProfile';

@Injectable({providedIn: 'root'})
export class UserService {
  private readonly apiUrl = 'http://localhost:5555/user/me';
  constructor(private http: HttpClient) { }

  user$ = this.http.get<UserProfile>(`${this.apiUrl}`).pipe(
    tap(console.log),
    catchError(this.handleError)
  );

    handleError(handleError: any) : Observable<never>{
      return throwError(handleError);
    }
}
