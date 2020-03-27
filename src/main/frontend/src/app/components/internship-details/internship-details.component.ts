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
  review: Review;
  replies: Comment[];
  replyForm: FormGroup;
  
  canAlter: boolean = false;
  canValidate: boolean = false;
  public id: number;

  constructor(private route: ActivatedRoute,
    public router: Router,
    private internshipService: InternshipService,
    private reviewService: ReviewService,
    private userService: UserService,
    private tokenStorageService: TokenStorageService,
    public dialog: MatDialog,
    private fb: FormBuilder) {

      this.replyForm = this.fb.group({
        content: '',
      });
  }

  ngOnInit(): void {
    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
    this.internshipService.getReview(this.id).subscribe(data => {
      this.review = data;
      this.internship = this.review.internship;
      
      this.canAlter = (this.tokenStorageService.getUser().id == this.internship.user.id);
      this.userService.hasRightsToAlter(this.tokenStorageService.getUser().id)
        .subscribe(hasRights => {
          if (!this.canAlter) this.canAlter = hasRights;
          this.canValidate = hasRights;
        });

      this.replies = this.review.replies;
      console.log(this.replies);
    });


  }

  upvoteReview() {
    this.reviewService.upvote(this.internship.id).subscribe(_ => {
      this.ngOnInit();
    });
  }

  downvoteReview() {
    this.reviewService.downvote(this.internship.id).subscribe(_ => {
      this.ngOnInit();
    });
  }

  addComment() {
    let content = this.replyForm.get('content').value;
    console.log(content)
    this.reviewService.addComment(this.internship.id, content).subscribe(_ => {
      this.ngOnInit();
    })
  }

  deleteReply(index: number) {
    let comment = this.replies[index];
    this.reviewService.deleteComment(this.internship.id, comment.id).subscribe(_ => {
      this.ngOnInit();
    })
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
