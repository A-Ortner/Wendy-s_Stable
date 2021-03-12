import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SportComponent} from './component/sport/sport.component';
import {HorseMainComponent} from './component/horse-main/horse-main.component';
import {HorseAddComponent} from './component/horse-add/horse-add.component';

const routes: Routes = [
  {path: '', redirectTo: 'sports', pathMatch: 'full'},
  {path: 'sports', component: SportComponent},
  {path: 'horses', component: HorseMainComponent},
  {path: 'add-horse', component: HorseAddComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
