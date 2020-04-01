export class CustomError{
    
    private type : string;
    get getType() : string{
        return this.type;
    }
    set setType(type : string){
        this.type = type;
    }
    private _message : string;
    get getMessage() : string {
        return this._message;
    }
    set setMessage(message : string) {
        this._message = message;
    } 

    constructor(type : string, message : string){
        this.type = type;
        this._message = message;
    }

};