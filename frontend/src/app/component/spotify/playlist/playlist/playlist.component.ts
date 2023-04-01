import {Component, Input, OnInit} from '@angular/core';
import { PlaylistsWithMinimalTrackInfo, Playlist } from '../../../../interface/generate_entities';
import { Observable, of, empty} from 'rxjs';

@Component({
  selector: 'playlist',
  templateUrl: './playlist.component.html',
  styleUrls: ['./playlist.component.scss']
})
export class PlaylistComponent implements OnInit {
  @Input() public playlist$: Observable<PlaylistsWithMinimalTrackInfo> = empty();
  @Input() public playlist: PlaylistsWithMinimalTrackInfo | null = null;
  @Input() public size: String  = "md";

  playlistImage$: Observable<String> = empty();
  constructor() { }

  ngOnInit(): void {
    if(this.playlist != null){
      this.playlist$ = of(this.playlist);
    }
  }
}
