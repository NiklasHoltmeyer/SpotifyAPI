import { Injectable } from '@angular/core';

@Injectable({providedIn: 'root'})
export class PlaylistService {
  private readonly apiUrl = 'http://localhost:5555/playlist/me';
  constructor(private http: HttpClient) { }

  user$ = this.http.get<UserProfile>(`${this.apiUrl}`).pipe(
    tap(console.log),
    catchError(this.handleError)
  );

    handleError(handleError: any) : Observable<never>{
      return throwError(handleError);
    }
}
