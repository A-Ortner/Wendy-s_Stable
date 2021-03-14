import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SportComponent} from './component/sport/sport.component';
import {HorseMainComponent} from './component/horse-main/horse-main.component';
import {HorseAddComponent} from './component/horse-add/horse-add.component';
import {HorseDetailsComponent} from './component/horse-details/horse-details.component';
import {HorseEditComponent} from './component/horse-edit/horse-edit.component';

const routes: Routes = [
  {path: '', redirectTo: 'sports', pathMatch: 'full'},
  {path: 'sports', component: SportComponent},
  {path: 'horses', component: HorseMainComponent},
  {path: 'add-horse', component: HorseAddComponent},
  {path: 'details-horse/:id', component: HorseDetailsComponent},
  {path: 'edit-horse/:id', component: HorseEditComponent},
  {path: 'details-horse/:id/edit-horse/:id', redirectTo: 'edit-horse/:id'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
