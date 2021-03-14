import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './component/header/header.component';
import {SportComponent} from './component/sport/sport.component';
import {HttpClientModule} from '@angular/common/http';
import { HorseMainComponent } from './component/horse-main/horse-main.component';
import { HorseAddComponent } from './component/horse-add/horse-add.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatInputModule} from '@angular/material/input';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatNativeDateModule, MatOptionModule} from '@angular/material/core';
import {MatDialogModule} from '@angular/material/dialog';
import { DialogComponent } from './component/dialog/dialog.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';

import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HorseDetailsComponent } from './component/horse-details/horse-details.component';
import { HorseEditComponent } from './component/horse-edit/horse-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SportComponent,
    HorseMainComponent,
    HorseAddComponent,
    DialogComponent,
    HorseDetailsComponent,
    HorseEditComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    MatDatepickerModule,
    BrowserAnimationsModule,
    MatDatepickerModule, MatInputModule, MatNativeDateModule,
    ReactiveFormsModule,
    MatDialogModule, MatOptionModule,
    MatFormFieldModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
    NgbModule
  ],
  exports: [
    MatInputModule,
    MatDialogModule,
    MatFormFieldModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
    MatDatepickerModule,
  ],
  providers: [MatDatepickerModule],
  bootstrap: [AppComponent]
})
export class AppModule {
}
