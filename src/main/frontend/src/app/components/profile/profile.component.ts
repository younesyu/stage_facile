import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.sass']
})
export class ProfileComponent implements OnInit {
  user: User;
  p = 1;

  constructor(private userService: UserService, 
    private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    let email = this.tokenStorage.getUser().username;
    this.userService.findUserByEmail(email).subscribe(data => {
      this.user = data;
    });

    console.log(this.user);
  }

}
