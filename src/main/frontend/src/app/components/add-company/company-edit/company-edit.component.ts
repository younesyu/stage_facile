import { Component, OnInit } from '@angular/core';
import { AddCompanyComponent } from '../add-company.component';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { CompanyService } from 'src/app/services/company.service';
import { IndustryService } from 'src/app/services/industry.service';

@Component({
  selector: 'app-company-edit',
  templateUrl: '../add-company.component.html',
  styleUrls: ['../add-company.component.sass']
})
export class CompanyEditComponent extends AddCompanyComponent implements OnInit {
  id: number;

  constructor(public fb: FormBuilder,
    private route: ActivatedRoute, 
    public router: Router, 
    public companyService: CompanyService, 
    public industryService: IndustryService) {
    super(fb, router, companyService, 
      industryService)
   }

  ngOnInit(): void {
    super.ngOnInit();

    this.title = "Mettre à jour la fiche d'entreprise"

    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
    this.companyService.get(this.id).subscribe(data => {
      this.companyForm.setValue(data);
    });
  }

  gotoParentPage() {
    this.router.navigate(['/company/' + this.id]);
  }
}
