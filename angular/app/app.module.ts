import { NgModule }      from '@angular/core';
import { APP_BASE_HREF } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule }   from '@angular/forms';
import { HttpModule }    from '@angular/http';
import {AgGridModule} from 'ag-grid-ng2/main';
import { routing, appRouterProviders } from './app.routing';
import { AppComponent }  from './app.component';

import { HomeComponent } from './home/home.component';
import { SearchParticipantsComponent } from './search/search.participants.component';

@NgModule({
    imports:      [ BrowserModule,
                    AgGridModule.withNg2ComponentSupport(),
                    FormsModule,
                    ReactiveFormsModule,
                    HttpModule,
                    routing
    ],
    declarations: [ AppComponent,
                    HomeComponent,
                    SearchParticipantsComponent
                  ],
    providers:    [ appRouterProviders,
                  [ {provide: APP_BASE_HREF, useValue: '/activities/ui/angular'} ]
    ],
    bootstrap:    [ AppComponent ]
})
export class AppModule { }
