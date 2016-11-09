import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators }      from '@angular/forms';
import { AccessService }                from 'app/access/access.service';

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
    public searchParticipantForm: FormGroup;

    constructor(private accessService: AccessService) {
        let fb = new FormBuilder();
        this.searchParticipantForm = fb.group({
            country: ['', <any>Validators.required],
            city: [''],
            addressLine1: [''],
            lastname: [''],
            id: ['']
        });
    }

    // on-init
    ngOnInit() {
        this.subscription = this.accessService.getAccessRights()
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
        this.subscription.unsubscribe();
    }

    searchParticipant(event) {
        console.log(this.searchParticipantForm.value);
        event.preventDefault();
    }
}
