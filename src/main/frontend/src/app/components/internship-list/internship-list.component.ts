import { Component, OnInit } from '@angular/core';
import { Internship } from '../../models/Internship';
import { InternshipService } from 'src/app/services/internship.service';

@Component({
  selector: 'app-internship-list',
  templateUrl: './internship-list.component.html',
  styleUrls: ['./internship-list.component.sass']
})
export class InternshipListComponent implements OnInit {

  internships: Internship[];
  p: number = 1;

  constructor(private internshipService: InternshipService) {
  }

  ngOnInit(): void {
    this.internshipService.findAll().subscribe(data => {
      this.internships = data;
    });
  } 

}
