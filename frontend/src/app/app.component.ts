import { Component, OnInit } from '@angular/core';
import { AlbumService } from './service/spotify/album.service';
import { UserProfile } from './interface/generate_entities';
import { Observable, of, empty} from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'frontend';
  user: Observable<UserProfile> = empty();


  constructor(private albumService: AlbumService){}

  ngOnInit() {
//     this.astring$ = this.albumService.album$;
  }

}
