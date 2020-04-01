export class ApiResponse {
    private response : string;
    private message : string;
    private data ?: any;

    constructor(response : string, message : string, data : any){
        this.response = response;
        this.message = message;
        this.data = data;
    }

    get getResponse() : string {
        return this.response;
    }

    get getMessage() : string {
        return this.message;
    }

    get getData() : any {
        return this.data;
    }

}