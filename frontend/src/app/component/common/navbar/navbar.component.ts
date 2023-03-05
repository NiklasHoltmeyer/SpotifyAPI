import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { UserService } from '../../../service/spotify/user.service';
import { UserProfile } from '../../../interface/UserProfile';
import { Observable, of, empty} from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
 user$: Observable<UserProfile> = empty();
  userImage$: Observable<String> = empty();
  private readonly apiLogin = 'http://localhost:5555/spotify/auth/';

  @ViewChild('navbaruserdropdown') userdropdown?: ElementRef<HTMLInputElement>;

  constructor(private userService: UserService){}

  ngOnInit(): void {
      this.user$ = this.userService.user$;
      this.user$.subscribe((data) => {
          this.userImage$ = of(data?.images[0]["url"]);
      });
  }

  login() : void{
    window.location.href = this.apiLogin;
  }

  ondropClick(id: String) {
    this.userdropdown?.dropdown('toggle');
  }
}
