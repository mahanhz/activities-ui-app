import { Component, OnInit, OnDestroy } from '@angular/core';
import { AccessService } from 'app/access/access.service';

interface Access {
    includeFacilitatedActivity: boolean;
    includeHostedActivity: boolean;
}

@Component({
    selector: 'home',
    templateUrl: 'app/home/home.component.html',
    providers: [AccessService]
})
export class HomeComponent implements OnInit, OnDestroy {

    private accessRights: Access;
    private subscription;

    constructor(private accessService: AccessService) {}

    // on-init
    ngOnInit() {
        this.subscription = this.accessService.getAccessRights()
            .subscribe(
                (data) => {
                    this.accessRights = data;
                },
                (err) => console.log(err),
                () => console.log('access service complete')
        );
    }

    // on-destroy
    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
