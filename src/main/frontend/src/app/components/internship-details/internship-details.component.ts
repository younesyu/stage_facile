import { Component, OnInit, Inject } from '@angular/core';
import { Internship } from 'src/app/models/Internship';
import { InternshipService } from 'src/app/services/internship.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { DeletionDialogComponent } from '../deletion-dialog/deletion-dialog.component';

@Component({
  selector: 'app-internship-details',
  templateUrl: './internship-details.component.html',
  styleUrls: ['./internship-details.component.sass']
})
export class InternshipDetailsComponent implements OnInit {

  public internship: Internship;
  public id: number;

  constructor(private route: ActivatedRoute,
    public router: Router,
    private internshipService: InternshipService,
    public dialog: MatDialog) { }

  ngOnInit(): void {
    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
    this.internshipService.get(this.id).subscribe(data => {
      this.internship = data;
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
    this.internshipService.delete(this.internship).subscribe(result => {
      this.gotoParentPage();
    });
  }

  gotoParentPage() {
    this.router.navigate(['/internships']);
  }
}
