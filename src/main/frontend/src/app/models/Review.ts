import { Internship } from './Internship';
import { Comment } from './Comment';

export class Review {
    internship: Internship;
    content: string;
    teamCommunication: string;
    easeOfIntegration: string;
    mentorship: string;
    subject: string;
    workload: string;
    wouldRecommend: string;
    averageReview: number;
    replies: Comment[];
    upvoters: number[];
    downvoters: number[];
}