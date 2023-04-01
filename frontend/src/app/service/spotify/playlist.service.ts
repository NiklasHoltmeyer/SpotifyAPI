import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import {Playlists, PlaylistsWithMinimalTrackInfo, Playlist} from '../../interface/generate_entities';

@Injectable({providedIn: 'root'})
export class PlaylistService {
  private readonly apiUrl = 'http://localhost:5555/playlist';
  constructor(private http: HttpClient) { }

  listCurrentUser() : Observable<PlaylistsWithMinimalTrackInfo[]>{
    return this.http.get<PlaylistsWithMinimalTrackInfo[]>(`${this.apiUrl}/me`);
  }

  get(id: string) : Observable<Playlist>{
    return this.http.get<Playlist>(`${this.apiUrl}/${id}`);
  }
}
