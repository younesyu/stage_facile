import { Component, OnInit } from '@angular/core';
import { Company } from 'src/app/models/Company';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CompanyService } from 'src/app/services/company.service';
import { IndustryService } from 'src/app/services/industry.service';
import countriesJson from 'src/assets/countries-FR.json'
import { Industry } from 'src/app/models/Industry';

@Component({
  selector: 'app-add-company',
  templateUrl: './add-company.component.html',
  styleUrls: ['./add-company.component.sass']
})
export class AddCompanyComponent implements OnInit {
  companyForm: FormGroup;

  countries: string[];
  industries: Industry[];
  
  constructor(private fb: FormBuilder,
    private route: ActivatedRoute, 
    private router: Router, 
    private companyService: CompanyService, 
    private industryService: IndustryService) { }

  ngOnInit(): void {
    this.countries = countriesJson.map(country => country.name);

    this.industryService.findAll().subscribe(data => {
      this.industries = data;
    }); 

    this.companyForm = this.fb.group({
      name: '',
      industry: '',
      headOfficeAddress: '',
      postalCode: ['', [
        Validators.pattern("^[0-9]{5}$"),
      ]],
      city: '',
      country: '',
    });

  }

  get postalCode() {
    return this.companyForm.get('postalCode').value
  }

  onSubmit():void {
    this.companyService.save(this.companyForm.value).subscribe(result => this.gotoCompanyList());
    
  }

  gotoCompanyList() {
    this.router.navigate(['/companies']);
  }

}
