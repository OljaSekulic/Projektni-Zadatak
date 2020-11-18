import { Post } from './post';

export class Rate {
    constructor(public id?: number,
        public rate?: number,
        public userName?: string,
        public post?: Post) { }
}
