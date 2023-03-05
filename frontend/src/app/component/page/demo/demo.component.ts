import { Component, OnInit } from '@angular/core';
import { UserProfile } from '../../../interface/UserProfile';
import { Followers } from '../../../interface/Followers';
import { Image } from '../../../interface/Image';
import { ExplicitContent } from '../../../interface/ExplicitContent';
import { Observable, of, empty} from 'rxjs';
import { UserService } from '../../../service/spotify/user.service';

@Component({
  selector: 'app-demo',
  templateUrl: './demo.component.html',
  styleUrls: ['./demo.component.scss']
})
export class DemoComponent implements OnInit {
  user$: Observable<UserProfile> = empty();

  constructor(private userService: UserService){}

    ngOnInit(): void {
        this.user$ = this.userService.user$;
//         this.user$.subscribe((data) => {
//             this.userImage$ = of(data?.images[0]["url"]);
//         });
    }
}
