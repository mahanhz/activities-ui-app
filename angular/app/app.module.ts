import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';

import { routing, appRouterProviders } from './app.routing';
import { AppComponent }  from './app.component';
import { HomeComponent } from './home/home.component';

@NgModule({
  imports:      [ BrowserModule,
                  FormsModule,
                  routing ],
  declarations: [ AppComponent,
                  HomeComponent ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
