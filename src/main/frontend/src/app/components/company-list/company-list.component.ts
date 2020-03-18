import { Component, OnInit } from '@angular/core';
import { Company } from 'src/app/models/Company';
import { CompanyService } from 'src/app/services/company.service';

@Component({
  selector: 'app-company-list',
  templateUrl: './company-list.component.html',
  styleUrls: ['./company-list.component.sass']
})
export class CompanyListComponent implements OnInit {

  companies: Company[];
  p: number = 1;

  constructor(private companyService: CompanyService) { }

  ngOnInit(): void {
    this.companyService.findAll().subscribe(data => {
      this.companies = data;
    });
  }

}
