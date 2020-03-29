export class ApiResponse {
    private response : string;
    private message : string;
    private data : string;

    constructor(response : string, message : string, data : string){
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

    get getData() : string {
        return this.data;
    }

}