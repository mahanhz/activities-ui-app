import { NgModule }      from '@angular/core';
import { APP_BASE_HREF } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule }    from '@angular/http';

import { routing, appRouterProviders } from './app.routing';
import { AppComponent }  from './app.component';
import { HomeComponent } from './home/home.component';

@NgModule({
    imports:      [ BrowserModule,
                    FormsModule,
                    HttpModule,
                    routing
    ],
    declarations: [ AppComponent,
                    HomeComponent
    ],
    providers:    [ appRouterProviders,
                  [ {provide: APP_BASE_HREF, useValue: '/activities/ui/angular'} ]
    ],
    bootstrap:    [ AppComponent ]
})
export class AppModule { }
