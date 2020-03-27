import { User } from './User';

export class Comment {
    id: number
    writer: User
    content: string
    postedOn: Date
    upvoters: number[]
    downvoters: number[]
}