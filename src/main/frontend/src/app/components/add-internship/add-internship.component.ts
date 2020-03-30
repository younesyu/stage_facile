import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import countriesJson from 'src/assets/countries-FR.json'
import { CompanyService } from 'src/app/services/company.service';
import { Company } from 'src/app/models/Company';
import { Industry } from 'src/app/models/Industry';
import { IndustryService } from 'src/app/services/industry.service';
import { UserService } from 'src/app/services/user.service';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

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
  filteredCompaniesString: Observable<string[]>;
  industriesString: string[];

  constructor(public fb: FormBuilder,
    public userService: UserService,
    public companyService: CompanyService,
    public industryService: IndustryService) {


    this.internshipInfoFormGroup = this.fb.group({
      beginDate: ['', [
        Validators.required,
        Validators.pattern("^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$")
      ]],
      endDate: ['', [
        Validators.required,
        Validators.pattern("^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$")
      ]],
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

      this.filteredCompaniesString = this.internshipInfoFormGroup.get('company').valueChanges
        .pipe(
          startWith(''),
          map(value => this._filter(value))
        );
    });

    this.industryService.findAll().subscribe(data => {
      this.industries = data;
      this.industriesString = data.map(industry => industry.name).filter(i => !(i.trim() == ""));
    });

    this.userService.getLoggedInUser().subscribe(data => {
      this.internshipInfoFormGroup.get('user').setValue(data);
    });

    this.internshipInfoFormGroup.get('company').valueChanges.subscribe(input => {
      if (!this.companiesString.includes(input)) {
        this.internshipInfoFormGroup.get('company').setErrors({
          valid: false
        });
      }
      else {
          this.internshipInfoFormGroup.get('company').setErrors(null);
        }
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
    if (beginDate - endDate > 0) {
      this.internshipInfoFormGroup.get('beginDate').setErrors({ valid: false });
      this.internshipInfoFormGroup.get('endDate').setErrors({ valid: false });
    } else {
      this.internshipInfoFormGroup.get('beginDate').setErrors(null);
      this.internshipInfoFormGroup.get('endDate').setErrors(null);
    }

    return (beginDate - endDate > 0)
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.companiesString.filter(option => option.toLowerCase().includes(filterValue));
  }
}
