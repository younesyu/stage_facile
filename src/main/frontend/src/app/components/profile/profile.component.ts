import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { UserService } from 'src/app/services/user.service';
import { InternshipService } from 'src/app/services/internship.service';
import { Internship } from 'src/app/models/Internship';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.sass']
})
export class ProfileComponent implements OnInit {
  user: User;
  internships: Internship[];
  p = 1;

  constructor(private userService: UserService,
    private internshipService: InternshipService,
    private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.userService.getLoggedInUser().subscribe(data => {
      this.user = data;
      this.user.role = this.tokenStorage.getUser().role;
      this.internshipService.getUserInternships(this.user.id).subscribe(data => {
        this.internships = data;
        console.log(data);
      });
    });


  }

}
