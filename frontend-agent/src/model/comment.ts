import { Post } from './post';

export class Comment {
    constructor(public id?: number,
        public content?: string,
        public commentDate?: Date,
        public userName?: string,
        public deleted?: boolean,
        public post?: Post) { }
}
