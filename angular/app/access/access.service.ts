import { Injectable, Inject } from '@angular/core';
import { Http, Response } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class AccessService {

    constructor(private http: Http) {}

    getAccessRights() {
        return this.http.get('access')
                        .map((res:Response) => res.json());
    }
}
