import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';

import { SearchService } from './search.service';
import { Participants } from './participants.interface';

@Component({
    selector: 'search-participants',
    templateUrl: 'app/search/search.participants.component.html'
    providers: [SearchService]
})
export class SearchParticipantsComponent {

    private participantsResponse: Participants;
    private searchParticipantForm: FormGroup;
    private searchSubscription;

    constructor(private searchService: SearchService) {
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
    }

    // on-destroy
    ngOnDestroy() {
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
