import { Comment } from './comment';
import { Rate } from './rate';
export class Post {
  id: number;
  text: string;
  user: string;
  comments: Comment[];
  rates: Rate[];
  postDate: Date;
  constructor(text?: string, user?: string) {

    this.text = text;
    this.user = user;
  }
}