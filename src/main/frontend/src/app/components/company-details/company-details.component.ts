import { Component, OnInit } from '@angular/core';
import { Company } from 'src/app/models/Company';
import { ActivatedRoute, Router } from '@angular/router';
import { CompanyService } from 'src/app/services/company.service';
import { Internship } from 'src/app/models/Internship';
import { MatDialog } from '@angular/material/dialog';
import { DeletionDialogComponent } from '../deletion-dialog/deletion-dialog.component';

@Component({
  selector: 'app-company-details',
  templateUrl: './company-details.component.html',
  styleUrls: ['./company-details.component.sass']
})
export class CompanyDetailsComponent implements OnInit {
  
  company: Company;
  internships: Internship[];
  public id: number;
  p = 1;

  constructor(private route: ActivatedRoute,
    public router: Router,
    private companyService: CompanyService,
    public dialog: MatDialog) { }

  ngOnInit(): void {
    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
    this.companyService.get(this.id).subscribe(data => {
      this.company = data;
    });

    this.companyService.getInternships(this.id).subscribe(data => {
      this.internships = data;
    });
  }
  
  openDialog(): void {
    const dialogRef = this.dialog.open(DeletionDialogComponent, {
      width: 'auto',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) this.delete();
    });
  }

  delete(): void {
    this.companyService.delete(this.company).subscribe(result => {
      this.gotoParentPage();
    });
  }

  gotoParentPage() {
    this.router.navigate(['/companies']);
  }
}
