import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';

import { AccessService } from 'app/access/access.service';
import { Access } from 'app/access/access.interface';
import { SearchService } from 'app/search/search.service';
import { Participants } from 'app/search/participants.interface';


@Component({
    selector: 'home',
    templateUrl: 'app/home/home.component.html',
    providers: [AccessService, SearchService]
})
export class HomeComponent implements OnInit, OnDestroy {

    private accessRights: Access;
    private participantsResponse: Participants;
    private searchParticipantForm: FormGroup;
    private accessSubscription;
    private searchSubscription;

    constructor(private accessService: AccessService, private searchService: SearchService) {
    }

    // on-init
    ngOnInit() {
        let fb = new FormBuilder();
        this.searchParticipantForm = fb.group({
            country: ['', <any>Validators.required],
            city: [''],
            addressLine1: [''],
            lastname: [''],
            id: ['']
        });

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
        this.searchSubscription.unsubscribe();
    }

    searchParticipant(event) {
        var country = this.searchParticipantForm.controls.country.value;
        var city = this.searchParticipantForm.controls.city.value;
        var addressLine1 = this.searchParticipantForm.controls.addressLine1.value;
        var lastName = this.searchParticipantForm.controls.lastname.value;
        var id = this.searchParticipantForm.controls.id.value;

        this.searchSubscription = this.searchService.getParticipants(country, city, addressLine1, lastName, id)
            .subscribe(
                (data) => {
                    this.participantsResponse = data;
                },
                (err) => console.log(err),
                () => console.log('Search service complete')
        );

        event.preventDefault();
    }
}
