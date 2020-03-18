import { Component, OnInit } from '@angular/core';
import { Company } from 'src/app/models/Company';
import { ActivatedRoute } from '@angular/router';
import { CompanyService } from 'src/app/services/company.service';
import { Internship } from 'src/app/models/Internship';

@Component({
  selector: 'app-company-details',
  templateUrl: './company-details.component.html',
  styleUrls: ['./company-details.component.sass']
})
export class CompanyDetailsComponent implements OnInit {
  
  company: Company;
  internships: Internship[];
  public id: number;
  p = 1;

  constructor(private route: ActivatedRoute, private companyService: CompanyService) { }

  ngOnInit(): void {
    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
    this.companyService.get(this.id).subscribe(data => {
      this.company = data;
    });

    this.companyService.getInternships(this.id).subscribe(data => {
      this.internships = data;
    });
  }

}
