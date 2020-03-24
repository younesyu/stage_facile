import { Component, OnInit } from '@angular/core';
import { Internship } from '../../models/Internship';
import { InternshipService } from 'src/app/services/internship.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-internship-list',
  templateUrl: './internship-list.component.html',
  styleUrls: ['./internship-list.component.sass']
})
export class InternshipListComponent implements OnInit {

  internships: Internship[];
  p: number = 1;
  private isConnected = false;

  constructor(private internshipService: InternshipService,
    private tokenStorage: TokenStorageService) {
  }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isConnected = true;
    }

    this.internshipService.findValidated().subscribe(data => {
      this.internships = data;
    });
  } 

  get isLoggedIn() {
    return this.isConnected;
  }

}
