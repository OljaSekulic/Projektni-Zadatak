import { UserTokenState } from './user-token-state';


export class LoggedUser {
    constructor(public id: number,
        public email: number,
        public token: UserTokenState) { }
}
