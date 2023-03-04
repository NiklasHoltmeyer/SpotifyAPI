import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

@Injectable({providedIn: 'root'})

export class AlbumService {
//   private readonly apiUrl = 'http://localhost:5555/user/me';
//   constructor(private http: HttpClient) { }
//
//   album$ = this.http.get<Album>(`${this.apiUrl}`).pipe(
//     tap(console.log),
//     catchError(this.handleError)
//   );
//
//     handleError(handleError: any) : Observable<never>{
//       if(handleError.status == 401){
//         window.open("https://www.w3schools.com"); //TODO redirct login page
//       }
//       return throwError(handleError);
//     }
}
