import { Component, OnInit } from '@angular/core';
import { Company } from 'src/app/models/Company';
import { CompanyService } from 'src/app/services/company.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-company-list',
  templateUrl: './company-list.component.html',
  styleUrls: ['./company-list.component.sass']
})
export class CompanyListComponent implements OnInit {

  companies: Company[];
  displayedCompanies: Company[];
  p: number = 1;
  private isConnected = false;

  constructor(private companyService: CompanyService,
    private router: Router,
    private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isConnected = true;
    } else {
      this.router.navigate(['/']);
    }

    this.companyService.findAll().subscribe(data => {
      this.companies = data;
      this.displayedCompanies = this.companies;
    });
  }

  get isLoggedIn() {
    return this.isConnected;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;

    let filteredCompanies =
      this.companies
        .filter(c => c.name.toLowerCase().includes(filterValue));

    let filteredIndustries =
      this.companies
        .map(i => i.industry.name)
        .filter(i => i.toLowerCase().includes(filterValue));

    let filteredCountries =
      this.companies
        .map(c => c.country)
        .filter(country => country.toLowerCase().includes(filterValue));

    this.displayedCompanies = this.companies.filter(c => 
      filteredCompanies.includes(c)
      || filteredIndustries.includes(c.industry.name)
      || filteredCountries.includes(c.country));
  }

}
