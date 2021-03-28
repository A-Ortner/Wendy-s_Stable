import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './component/header/header.component';
import {SportComponent} from './component/sport/sport.component';
import {HttpClientModule} from '@angular/common/http';
import {HorseMainComponent} from './component/horse-main/horse-main.component';
import {HorseAddComponent} from './component/horse-add/horse-add.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HorseDetailsComponent} from './component/horse-details/horse-details.component';
import {HorseEditComponent} from './component/horse-edit/horse-edit.component';
import {SportAddComponent} from './component/sport-add/sport-add.component';
import {BloodlineComponent} from './component/bloodline/bloodline.component';
import {TreeElementComponent} from './component/tree-element/tree-element.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SportComponent,
    HorseMainComponent,
    HorseAddComponent,
    HorseDetailsComponent,
    HorseEditComponent,
    SportAddComponent,
    BloodlineComponent,
    TreeElementComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgbModule
  ],
  exports: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
