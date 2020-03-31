import { Component, OnInit } from '@angular/core';
import { InternshipFormComponent } from '../internship-form.component';
import { Router, ActivatedRoute } from '@angular/router';
import { InternshipService } from 'src/app/services/internship.service';
import { Review } from 'src/app/models/Review';

@Component({
  selector: 'app-edit-internship-form',
  templateUrl: '../internship-form.component.html',
  styleUrls: ['../internship-form.component.sass']
})
export class EditInternshipFormComponent extends InternshipFormComponent implements OnInit {
  id: number;
  constructor(public router: Router,
    private route: ActivatedRoute,
    public internshipService: InternshipService) {
    super(router, internshipService);
    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
  }

  ngOnInit(): void {
    this.internshipService.getReview(this.id).subscribe(data => {
      this.infoFormGroup.patchValue(data.internship);

      this.infoFormGroup.get('company').setValue(data.internship.company.name)
      this.infoFormGroup.get('industry').setValue(data.internship.industry.name)
      data = this.parseReview(data)
      this.reviewFormGroup.patchValue(data);
      this.addReviewComponent.editMode = true
    }, err => {
      this.internshipService.get(this.id).subscribe(data => {
        this.infoFormGroup.patchValue(data);
        this.infoFormGroup.get('company').setValue(data.company.name)
        this.infoFormGroup.get('industry').setValue(data.industry.name)
      });
    });
  }

  parseReview(review: Review) {
    review.easeOfIntegration = this.parseEReview(review.easeOfIntegration);
    review.mentorship = this.parseEReview(review.mentorship);
    review.subject = this.parseEReview(review.subject);
    review.teamCommunication = this.parseEReview(review.teamCommunication);
    review.workload = this.parseEReview(review.workload);
    review.wouldRecommend = this.parseEReview(review.wouldRecommend);

    return review;
  }

  parseEReview(Ereview: string) {
    switch (Ereview) {
      case "REVIEW_TERRIBLE":
        return "1";
      case "REVIEW_POOR":
        return "2";
      case "REVIEW_AVERAGE":
        return "3";
      case "REVIEW_GOOD":
        return "4";
      case "REVIEW_EXCELLENT":
        return "5";
      default:
        return null;
    }
  }

}
