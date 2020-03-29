import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { UserService } from 'src/app/services/user.service';
import { InternshipService } from 'src/app/services/internship.service';
import { Internship } from 'src/app/models/Internship';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.sass']
})
export class ProfileComponent implements OnInit {
  user: User;
  id: number;
  internships: Internship[];
  p = 1;

  constructor(private userService: UserService,
    private route: ActivatedRoute,
    private internshipService: InternshipService,
    private tokenStorage: TokenStorageService) {
    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
  }

  ngOnInit(): void {
    this.userService.get(this.id).subscribe(data => {
      this.user = data;
      console.log(data)
      this.internshipService.getUserInternships(this.user.id).subscribe(data => {
        this.internships = data;
      });
    });


  }

}
