import { Component, OnInit, ViewChild } from '@angular/core';
import { AddInternshipComponent } from '../add-internship.component';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { InternshipService } from 'src/app/services/internship.service';
import { CompanyService } from 'src/app/services/company.service';
import { IndustryService } from 'src/app/services/industry.service';
import { MatSelect } from '@angular/material/select';

@Component({
  selector: 'app-internship-edit',
  templateUrl: '../add-internship.component.html',
  styleUrls: ['../add-internship.component.sass']
})
export class InternshipEditComponent extends AddInternshipComponent implements OnInit {
  id: number;

  constructor(public fb: FormBuilder,
    private route: ActivatedRoute,
    public router: Router,
    public userService: UserService,
    public internshipService: InternshipService,
    public companyService: CompanyService,
    public industryService: IndustryService) {
    super(fb, userService, 
      companyService, industryService);
      this.editMode = true;
  }

  ngOnInit(): void {
    super.ngOnInit();

    this.title = "Mettre à jour la fiche de stage"

    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
    this.internshipService.get(this.id).subscribe(user => {
      this.internshipInfoFormGroup.patchValue(user);
      this.internshipInfoFormGroup.get('company').setValue(user.company.name);
      this.internshipInfoFormGroup.get('industry').setValue(user.industry.name);
      user.managers.forEach(manager => this.managers.push(this.fb.control(manager)));
    });
  }

  gotoParentPage() {
    this.router.navigate(['/internship/' + this.id]);
  }
}
