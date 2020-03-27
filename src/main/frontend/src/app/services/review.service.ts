import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TokenStorageService } from './token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private reviewsUrl: string;
  
  constructor(private http: HttpClient,
    private tokenStorageService: TokenStorageService) {
    this.reviewsUrl = "http://localhost:8080/reviews/";
  }

  upvote(reviewId: number) {
    let userId = this.tokenStorageService.getUser().id;
    let requestBody = {
      "voterId": userId,
      "reviewId": reviewId
    }
    return this.http.post<null>(this.reviewsUrl + "upvoteReview", requestBody);
  }

  downvote(reviewId: number) {
    let userId = this.tokenStorageService.getUser().id;
    let requestBody = {
      "voterId": userId,
      "reviewId": reviewId
    }
    return this.http.post<null>(this.reviewsUrl + "downvoteReview", requestBody);
  }

  addComment(reviewId: number, content: string) {
    let requestBody = {
      "reviewId": reviewId,
      "posterId": this.tokenStorageService.getUser().id,
      "content": content,
    }
    return this.http.post<null>(this.reviewsUrl + "addReply", requestBody);
  }

  deleteComment(reviewId: number, commentId: number) {
    let requestBody = {
      "reviewId": reviewId,
      "commentId": commentId
    }
    return this.http.post<null>(this.reviewsUrl + "deleteReply", requestBody);
  }

}
