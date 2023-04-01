import { Component, OnInit } from '@angular/core';
import { UserProfile, PlaylistsWithMinimalTrackInfo, Playlist } from '../../../interface/generate_entities';
import { Observable, of, empty} from 'rxjs';
import { UserService } from '../../../service/spotify/user.service';
import { tap, catchError } from 'rxjs/operators';
import { PlaylistService } from '../../../service/spotify/playlist.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  user$: Observable<UserProfile> = empty();
  playlistsCurrentUser$: Observable<PlaylistsWithMinimalTrackInfo[]> = empty();
  playlists$: Observable<PlaylistsWithMinimalTrackInfo[]> = empty();

  constructor(private userService: UserService, private playlistService: PlaylistService){}

    ngOnInit(): void {
        this.user$ = this.userService.user$;
        this.playlistsCurrentUser$ = this.playlistService.listCurrentUser();
//         this.playlistsCurrentUser$.subscribe((data) => {
//           //this.userImage$ = of(data?.images[0]["url"]);
//           this.playlist$ = of(data?.[0]);
//           console.log(data[0]);
//         });
    }
}
