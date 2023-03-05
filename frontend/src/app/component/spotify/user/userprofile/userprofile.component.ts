import {Component, Input, OnInit} from '@angular/core';
import { UserProfile } from '../../../../interface/UserProfile';
import { Observable, of, empty} from 'rxjs';

@Component({
  selector: 'userprofile',
  templateUrl: './userprofile.component.html',
  styleUrls: ['./userprofile.component.scss']
})
export class UserprofileComponent implements OnInit {
  @Input() public user$: Observable<UserProfile> = empty();
  @Input() public size: String  = "md";

  userImage$: Observable<String> = empty();
  constructor() { }

  ngOnInit(): void {

    this.user$.subscribe((data) => {
      this.userImage$ = of(data?.images[0]["url"]);
    });
  }
}
