export class UserTokenState {

    constructor(public jwt: string,
        public expiresIn: number) { }

}