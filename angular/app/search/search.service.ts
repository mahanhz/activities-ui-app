import { Injectable, Inject } from '@angular/core';
import { Http, Response } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class SearchService {

    constructor(private http: Http) {}

    getParticipants(private country: string,
                    private city: string,
                    private addressLine1: string,
                    private lastName: string,
                    private id: string) {

        var uri = "search?country=" + country + "&city=" + city + "&addressLine1=" + addressLine1 + "&lastName=" + lastName + "&participantId=" + id;
        return this.http.get(uri)
                        .map((res:Response) => res.json());
    }
}
