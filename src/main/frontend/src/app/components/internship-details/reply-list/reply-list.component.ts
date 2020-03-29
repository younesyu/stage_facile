import { Component, OnInit, Input } from '@angular/core';
import { Internship } from 'src/app/models/Internship';
import { Comment } from 'src/app/models/Comment';
import { Review } from 'src/app/models/Review';
import { ReviewService } from 'src/app/services/review.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { InternshipService } from 'src/app/services/internship.service';
import { UserService } from 'src/app/services/user.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-reply-list',
  templateUrl: './reply-list.component.html',
  styleUrls: ['./reply-list.component.sass']
})
export class ReplyListComponent implements OnInit {

  @Input('internship') internship: Internship;
  review: Review;

  replies: Comment[];
  p = 1;
  replyForm: FormGroup;


  constructor(private reviewService: ReviewService,
    private intershipService: InternshipService,
    private userService: UserService,
    private tokenStorageService: TokenStorageService,
    private fb: FormBuilder) {
    this.replyForm = this.fb.group({
      content: '',
    });
  }

  ngOnInit(): void {
    this.intershipService.getReview(this.internship.id).subscribe(data => {
      this.review = data;
      this.review.replies = this.review.replies.sort((a, b) => b.id - a.id);

      this.replies = this.review.replies;
    })
  }

  canDelete(comment: Comment) {
    let connectedUser = this.tokenStorageService.getUser();
    return (connectedUser.role === "ROLE_ADMIN"
      || connectedUser.role === "ROLE_MODERATOR"
      || comment.writer.id === connectedUser.id);
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

  upvoted(item: any) {
    return item.upvoters.includes(this.tokenStorageService.getUser().id)
  }
  downvoted(item: any) {
    return item.downvoters.includes(this.tokenStorageService.getUser().id)
  }

  addComment() {
    let content = this.replyForm.get('content').value;

    this.reviewService.addComment(this.internship.id, content).subscribe(_ => {
      this.replyForm.get('content').setValue('');
      this.replyForm.get('content').setErrors(null);
      this.ngOnInit();
    })
  }

  deleteComment(index: number) {
    let comment = this.replies[index];
    this.reviewService.deleteComment(this.internship.id, comment.id).subscribe(_ => {
      this.ngOnInit();
    })
  }

  upvoteComment(index: number) {
    let comment = this.replies[index];
    this.reviewService.upvoteComment(comment.id).subscribe(_ => {
      this.ngOnInit();
    })
  }

  downvoteComment(index: number) {
    let comment = this.replies[index];
    this.reviewService.downvoteComment(comment.id).subscribe(_ => {
      this.ngOnInit();
    })
  }

  getColor(review: string) {
    switch (review) {
      case "REVIEW_TERRIBLE":
        return "red"
      case "REVIEW_POOR":
        return "orange"
      case "REVIEW_AVERAGE":
        return "yellow"
      case "REVIEW_GOOD":
        return "lightgreen"
      case "REVIEW_EXCELLENT":
        return "green"
      default:
        break;
    }
  }

  getRecommandation(review: string) {
    switch (review) {
      case "REVIEW_TERRIBLE":
        return "Ne recommande pas"
      case "REVIEW_POOR":
        return "Recommande peu"
      case "REVIEW_AVERAGE":
        return "Recommande"
      case "REVIEW_GOOD":
        return "Recommande beaucoup"
      case "REVIEW_EXCELLENT":
        return "Recommande fortement"
      default:
        break;
    }
  }

}
