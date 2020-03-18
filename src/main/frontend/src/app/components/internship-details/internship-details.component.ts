import { Component, OnInit } from '@angular/core';
import { Internship } from 'src/app/models/Internship';
import { InternshipService } from 'src/app/services/internship.service';
import { ActivatedRoute } from '@angular/router';
import { Company } from 'src/app/models/Company';

@Component({
  selector: 'app-internship-details',
  templateUrl: './internship-details.component.html',
  styleUrls: ['./internship-details.component.sass']
})
export class InternshipDetailsComponent implements OnInit {
  
  public internship: Internship;
  public id: number;
  
  constructor(private route: ActivatedRoute, private internshipService: InternshipService) { }

  ngOnInit(): void {
    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
    this.internshipService.get(this.id).subscribe(data => {
      this.internship = data;
    });
  }

}
