import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { UserService } from 'src/app/services/user.service';
import { ActivatedRoute } from '@angular/router';
import { Internship } from 'src/app/models/Internship';
import { InternshipService } from 'src/app/services/internship.service';

@Component({
  selector: 'app-student-details',
  templateUrl: './student-details.component.html',
  styleUrls: ['./student-details.component.sass']
})
export class StudentDetailsComponent implements OnInit {

  public user: User;
  public id: number;
  public internships: Internship[];
  public p = 1

  constructor(private route: ActivatedRoute, 
    private userService: UserService,
    private internshipService: InternshipService) { }

  ngOnInit(): void {
    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
    this.userService.get(this.id).subscribe(data => {
      this.user = data;
    });

    this.internshipService.getUserInternships(this.id).subscribe(data => {
      this.internships = data;
    });

    console.log(this.internships);
  }

}
