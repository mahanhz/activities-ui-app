import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { GridOptions, IFilter } from 'ag-grid/main';

import { SearchService } from './search.service';
import { Participants } from './participants.interface';

@Component({
    selector: 'search-participants',
    templateUrl: 'app/search/search.participants.component.html',
    providers: [SearchService]
})
export class SearchParticipantsComponent implements OnInit, OnDestroy {

    private participantsResponse: Participants;
    private searchParticipantForm: FormGroup;
    private searchSubscription;

    // search results
    private gridOptions:GridOptions;
    private rowData:any[];

    constructor(private searchService: SearchService) {
        // we pass an empty gridOptions in, so we can grab the api out
        this.gridOptions = <GridOptions>{};
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
                    this.createResultsData();
                },
                (err) => console.log(err),
                () => console.log('Search service complete')
        );

        event.preventDefault();
    }

    private createResultsData() {
        var rowData:any[] = [];

        for (var i = 0; i < this.participantsResponse.participants.length; i++) {
            rowData.push({
                firstName: this.participantsResponse.participants[i].name.firstName,
                middleName: this.participantsResponse.participants[i].name.middleName,
                lastName: this.participantsResponse.participants[i].name.lastName,
                suffix: this.participantsResponse.participants[i].name.suffix,
                addressLine1: this.participantsResponse.participants[i].address.addressLine1,
                addressLine2: this.participantsResponse.participants[i].address.addressLine2,
                city: this.participantsResponse.participants[i].address.city,
                postalCode: this.participantsResponse.participants[i].address.postalCode,
                country: this.participantsResponse.participants[i].address.country,
                email: this.participantsResponse.participants[i].email,
                contactNumber: this.participantsResponse.participants[i].contactNumber,
                participantId: this.participantsResponse.participants[i].participantId
            });
        }

        this.rowData = rowData;
    }

    private onQuickFilterChanged($event) {
        this.gridOptions.api.setQuickFilter($event.target.value);
    }
}
