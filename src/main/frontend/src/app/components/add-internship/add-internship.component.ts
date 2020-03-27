import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import countriesJson from 'src/assets/countries-FR.json'
import { CompanyService } from 'src/app/services/company.service';
import { Company } from 'src/app/models/Company';
import { Industry } from 'src/app/models/Industry';
import { IndustryService } from 'src/app/services/industry.service';
import { InternshipService } from 'src/app/services/internship.service';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-add-internship',
  templateUrl: './add-internship.component.html',
  styleUrls: ['./add-internship.component.sass']
})
export class AddInternshipComponent implements OnInit {
  internshipInfoFormGroup: FormGroup;
  editMode: boolean = false;
  submitted: boolean = false;

  /**
   * Données de la base
   */
  companies: Company[];
  industries: Industry[];

  /**
   * Valeurs par défaut
   */
  title = "Ajouter un stage"
  countries: string[];
  experienceLevels: string[];
  wayOfFinding: string[];
  companiesString: string[];
  industriesString: string[];

  constructor(public fb: FormBuilder,
    public userService: UserService,
    public companyService: CompanyService,
    public industryService: IndustryService) {


    this.internshipInfoFormGroup = this.fb.group({
      beginDate: '',
      endDate: '',
      function: '',
      description: '',
      location: '',
      stipend: ['', [
        Validators.pattern("^([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(\.[0-9][0-9]?)?$"),
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
      user: '',
      id: '',
      validated: 'false',
    });

    this.countries = countriesJson.map(country => country.name);

    this.experienceLevels = [
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

  }

  ngOnInit(): void {

    this.companyService.findAll().subscribe(data => {
      this.companies = data;
      this.companiesString = data.map(company => company.name);
    });

    this.industryService.findAll().subscribe(data => {
      this.industries = data;
      this.industriesString = data.map(industry => industry.name);
    });

    this.userService.getLoggedInUser().subscribe(data => {
      this.internshipInfoFormGroup.get('user').setValue(data);
    });
  }

  get conventionReference() {
    return this.internshipInfoFormGroup.get('conventionReference')
  }
  get managers() {
    return this.internshipInfoFormGroup.get('managers') as FormArray
  }

  addManager() {
    if (this.managers.length < 5) {
      const manager = this.fb.control('');

      this.managers.push(manager);
    }
  }

  deleteManager(i) {
    this.managers.removeAt(i);
  }

  dateCompare() {
    let beginDate = Date.parse(this.internshipInfoFormGroup.get('beginDate').value);
    let endDate = Date.parse(this.internshipInfoFormGroup.get('endDate').value);
    
    if(beginDate - endDate > 0) {
      this.internshipInfoFormGroup.get('beginDate').setErrors({ valid : false });
      this.internshipInfoFormGroup.get('endDate').setErrors({ valid : false });
    } else {
      this.internshipInfoFormGroup.get('beginDate').setErrors(null);
      this.internshipInfoFormGroup.get('endDate').setErrors(null);
    }

    return (beginDate - endDate > 0)
  }
}
