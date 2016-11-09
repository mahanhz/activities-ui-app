import { Component, OnInit, OnDestroy } from '@angular/core';

import { AccessService } from 'app/access/access.service';
import { Access } from 'app/access/access.interface';

@Component({
    selector: 'home',
    templateUrl: 'app/home/home.component.html',
    providers: [AccessService]
})
export class HomeComponent implements OnInit, OnDestroy {

    private accessRights: Access;
    private accessSubscription;

    constructor(private accessService: AccessService) {
    }

    // on-init
    ngOnInit() {
        this.accessSubscription = this.accessService.getAccessRights()
            .subscribe(
                (data) => {
                    this.accessRights = data;
                },
                (err) => console.log(err),
                () => console.log('Access service complete')
        );
    }

    // on-destroy
    ngOnDestroy() {
        this.accessSubscription.unsubscribe();
    }
}
