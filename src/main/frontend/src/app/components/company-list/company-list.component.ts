import { Component, OnInit } from '@angular/core';
import { Company } from 'src/app/models/Company';
import { CompanyService } from 'src/app/services/company.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-company-list',
  templateUrl: './company-list.component.html',
  styleUrls: ['./company-list.component.sass']
})
export class CompanyListComponent implements OnInit {

  companies: Company[];
  p: number = 1;
  private isConnected = false;

  constructor(private companyService: CompanyService,
    private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isConnected = true;
    }

    this.companyService.findAll().subscribe(data => {
      this.companies = data;
    });
  }

  get isLoggedIn() {
    return this.isConnected;
  }

}
