import { Component, OnInit } from '@angular/core';
import { Internship } from '../../models/Internship';
import { InternshipService } from 'src/app/services/internship.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-internship-list',
  templateUrl: './internship-list.component.html',
  styleUrls: ['./internship-list.component.sass']
})
export class InternshipListComponent implements OnInit {

  internships: Internship[];
  displayedInternships: Internship[];
  p: number = 1;
  private isConnected = false;

  constructor(private internshipService: InternshipService,
    private router: Router,
    private tokenStorage: TokenStorageService) {
  }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isConnected = true;
    } else {
      this.router.navigate(['/']);
    }

    this.internshipService.findValidated().subscribe(data => {
      this.internships = data;
      this.displayedInternships = this.internships;
    });
  }

  get isLoggedIn() {
    return this.isConnected;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    let filteredConventionReferences =
      this.internships
        .map(i => i.conventionReference.toString())
        .filter(c => c.includes(filterValue));

    let filteredFunctions =
    this.internships
      .map(i => i.function)
      .filter(f => f.toLowerCase().includes(filterValue));

    let filteredCompanies =
      this.internships
        .map(i => i.company.name)
        .filter(c => c.toLowerCase().includes(filterValue));

    let filteredIndustries =
      this.internships
        .map(i => i.industry.name)
        .filter(i => i.toLowerCase().includes(filterValue));

    this.displayedInternships = this.internships.filter(i => 
      filteredConventionReferences.includes(i.conventionReference.toString()) 
      || filteredFunctions.includes(i.function)
      || filteredCompanies.includes(i.company.name)
      || filteredIndustries.includes(i.industry.name));
  }

}
