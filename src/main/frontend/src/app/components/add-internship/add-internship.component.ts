import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { Internship } from 'src/app/models/Internship';
import countriesJson from 'src/assets/countries-FR.json'
import { CompanyService } from 'src/app/services/company.service';
import { Company } from 'src/app/models/Company';
import { Industry } from 'src/app/models/Industry';
import { IndustryService } from 'src/app/services/industry.service';
import { InternshipService } from 'src/app/services/internship.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-internship',
  templateUrl: './add-internship.component.html',
  styleUrls: ['./add-internship.component.sass']
})
export class AddInternshipComponent implements OnInit {
  internship: Internship;
  internshipForm: FormGroup;

  countries: string[];
  values: string[];
  wayOfFinding: string[];
  companies: Company[];
  industries: Industry[];

  constructor(private fb: FormBuilder,
    private route: ActivatedRoute, 
    private router: Router, 
    private internshipService: InternshipService,
    private companyService: CompanyService, 
    private industryService: IndustryService) { }
  
  ngOnInit(): void {
    this.countries = countriesJson.map(country => country.name);

    this.values = [
      "Collège, lycée...",
      "Bac",
      "Bac + 1",
      "Bac + 2",
      "Bac + 3",
      "Bac + 4",
      "Bac + 5",
      "Bac + 6 & +",
    ];

    this.wayOfFinding = [
      "Stage trouvé par l'étudiant",
      "Stage proposé par l'université",
      "Stage proposé par l'entreprise",
      "Autre"
    ];

    this.companyService.findAll().subscribe(data => {
      this.companies = data;
    }); 

    this.industryService.findAll().subscribe(data => {
      this.industries = data;
    }); 

    this.internshipForm = this.fb.group({
      beginDate : '',
      endDate : '',
      function : '',
      description : '',
      location : '',
      stipend : ['', [
        Validators.pattern("^([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?$"),
        Validators.maxLength(20),
      ]],
      conventionReference: ['', [
        Validators.pattern("^[0-9]*$"),
        Validators.minLength(4),
        Validators.maxLength(20),
      ]],
      experienceLevel: '',
      managers: this.fb.array([]),
      foundBy: '',
      company: '',
      industry: '',
    });
  }
  get conventionReference() {
    return this.internshipForm.get('conventionReference')
  }
  get managers() {
    return this.internshipForm.get('managers') as FormArray
  }

  addManager() {
    if(this.managers.length < 5) {
      const manager = this.fb.control('');

      this.managers.push(manager);
    }
  }

  deleteManager(i) {
    this.managers.removeAt(i);
  }

  onSubmit():void {
    this.internship = new Internship();

    this.internship.beginDate = this.internshipForm.get('beginDate').value;
    this.internship.endDate = this.internshipForm.get('endDate').value;
    this.internship.function = this.internshipForm.get('function').value;
    this.internship.description = this.internshipForm.get('description').value;
    this.internship.location = this.internshipForm.get('location').value;
    this.internship.stipend = this.internshipForm.get('stipend').value;
    this.internship.conventionReference = this.internshipForm.get('conventionReference').value;
    this.internship.experienceLevel = this.internshipForm.get('experienceLevel').value;
    this.internship.managers = this.internshipForm.get('managers').value;
    this.internship.foundBy = this.internshipForm.get('foundBy').value;
    if(this.internshipForm.get('company').value !== '')
      this.internship.company = this.internshipForm.get('company').value;
    if(this.internshipForm.get('industry').value !== '')
      this.internship.industry = this.internshipForm.get('industry').value;
    else
      this.internship.industry = null;

    this.internshipService.save(this.internship).subscribe(result => this.gotoInternshipList());
    
  }

  gotoInternshipList() {
    this.router.navigate(['/internships']);
  }

  dateCompare() {
    console.log("here");
    if(this.internshipForm.get('beginDate').value - this.internshipForm.get('endDate').value > 0) {
      this.internshipForm.get('endDate').setErrors({
        invalid: true
      });
      console.log(this.internshipForm.get('beginDate').value - this.internshipForm.get('endDate').value);
    }
    else this.internshipForm.get('endDate').setErrors(null);
  }
}
