import { Component, OnInit } from '@angular/core';
import { AddInternshipComponent } from '../add-internship.component';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { InternshipService } from 'src/app/services/internship.service';
import { CompanyService } from 'src/app/services/company.service';
import { IndustryService } from 'src/app/services/industry.service';

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
    super(fb, router, userService, 
      internshipService, companyService, 
      industryService);
   }

  ngOnInit(): void {
    super.ngOnInit();

    this.title = "Mettre à jour la fiche de stage"

    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
    this.internshipService.get(this.id).subscribe(data => {
      this.internshipForm.setValue(data);
    });
  }


  gotoParentPage() {
    this.router.navigate(['/internship/' + this.id]);
  }
}
