import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { AddInternshipComponent } from '../add-internship/add-internship.component';
import { AddReviewComponent } from '../add-review/add-review.component';
import { Internship } from 'src/app/models/Internship';
import { Router } from '@angular/router';
import { InternshipService } from 'src/app/services/internship.service';
import { Company } from 'src/app/models/Company';
import { Industry } from 'src/app/models/Industry';

@Component({
  selector: 'app-internship-form',
  templateUrl: './internship-form.component.html',
  styleUrls: ['./internship-form.component.sass']
})
export class InternshipFormComponent implements OnInit {

  @ViewChild('infoForm', { static: false }) addInternshipComponent;
  @ViewChild('reviewForm', { static: false }) addReviewComponent;
  infoFormGroup: FormGroup;
  reviewFormGroup: FormGroup;
  private internship: Internship;

  constructor(
    public router: Router,
    public internshipService: InternshipService) {
  }

  ngOnInit(): void {

  }

  ngAfterViewInit() {
    this.infoFormGroup = this.addInternshipComponent.internshipInfoFormGroup;
    this.reviewFormGroup = this.addReviewComponent.internshipReviewFormGroup;
  }

  onSubmit(): void {
    this.infoFormGroup.get('validated').setValue(false);
    let selectedCompany: string = this.infoFormGroup.get('company').value;
    this.infoFormGroup.get('company').setValue(this.getCompanyByName(selectedCompany));
    let selectedIndustry: string = this.infoFormGroup.get('industry').value;
    this.infoFormGroup.get('industry').setValue(this.getIndustryByName(selectedIndustry));
    this.internshipService.save(this.infoFormGroup.value).subscribe(result => {
      let reviewObject = { "internship": result }
      Object.assign(reviewObject, this.reviewFormGroup.value);

      this.internshipService.addReview(result.id, this.reviewFormGroup.value).subscribe(_ => {
        this.gotoParentPage();
      })
    });
  }

  getCompanyByName(companyName: string): Company {
    return this.addInternshipComponent.companies.find(c => c.name == companyName);
  }

  getIndustryByName(industryName: string): Industry {
    return this.addInternshipComponent.industries.find(i => i.name == industryName);
  }

  gotoParentPage() {
    this.router.navigate(['/internships']);
  }
}
