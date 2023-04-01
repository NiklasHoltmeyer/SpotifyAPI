import { Component, OnInit } from '@angular/core';
import { UserProfile } from '../../../interface/UserProfile';
import { Observable, of, empty} from 'rxjs';
import { UserService } from '../../../service/spotify/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  user$: Observable<UserProfile> = empty();

  constructor(private userService: UserService){}

    ngOnInit(): void {
        this.user$ = this.userService.user$;
    }
}
