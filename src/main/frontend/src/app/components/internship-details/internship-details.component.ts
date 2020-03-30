import { Component, OnInit, Inject } from '@angular/core';
import { Internship } from 'src/app/models/Internship';
import { InternshipService } from 'src/app/services/internship.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { DeletionDialogComponent } from '../deletion-dialog/deletion-dialog.component';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { UserService } from 'src/app/services/user.service';
import { Review } from 'src/app/models/Review';
import { Comment } from 'src/app/models/Comment';
import { ReviewService } from 'src/app/services/review.service';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-internship-details',
  templateUrl: './internship-details.component.html',
  styleUrls: ['./internship-details.component.sass']
})
export class InternshipDetailsComponent implements OnInit {

  public internship: Internship;
  managersStr: string = "/"

  canAlter: boolean = false;
  canValidate: boolean = false;
  public id: number;

  constructor(private route: ActivatedRoute,
    public router: Router,
    private internshipService: InternshipService,
    private userService: UserService,
    private tokenStorageService: TokenStorageService,
    public dialog: MatDialog, ) {
    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
  }

  ngOnInit(): void {
    this.internshipService.get(this.id).subscribe(data => {
      this.internship = data

      this.canAlter = (this.tokenStorageService.getUser().id == this.internship.user.id);
      this.userService.hasRightsToAlter(this.tokenStorageService.getUser().id)
        .subscribe(hasRights => {
          if (!this.canAlter) this.canAlter = hasRights;
          this.canValidate = hasRights;
        });
      if (this.internship.managers.length != 0) {
        this.managersStr = this.internship.managers[0]
        for (let i = 1; i < this.internship.managers.length; i++) {
          let manager = this.internship.managers[i];
          this.managersStr = this.managersStr + ", " + manager;
        }
      }
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

  validateInternship() {
    this.internship.validated = true;
    this.internshipService.save(this.internship).subscribe(data => {
      window.location.reload();
    });
  }

  gotoParentPage() {
    this.router.navigate(['/internships']);
  }
}
