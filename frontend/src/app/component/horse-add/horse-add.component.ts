import {Component, Inject, OnInit} from '@angular/core';
import { Horse } from '../../dto/horse';
import {HorseService} from '../../service/horse.service';
import {Location} from '@angular/common';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MAT_DIALOG_DATA, MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material/dialog';
import {DialogComponent} from '../dialog/dialog.component';
//import {DialogComponent} from "../dialog/dialog.component";

@Component({
  selector: 'app-horse-add',
  templateUrl: './horse-add.component.html',
  styleUrls: ['./horse-add.component.scss']
})
export class HorseAddComponent implements OnInit {

  error = false;
  errorMessage = '';
  horse: Horse;

  constructor(private horseService: HorseService,
              private location: Location,
              /*private dialogRef: MatDialogRef<HorseAddComponent>,
              @Inject(MAT_DIALOG_DATA) data*/) {

    //this.horse=data.horse;
  }

  ngOnInit(): void {
    this.horse = new Horse();
  }

  /**
   * Sets location to previous page
   */
  goBack() {
    this.horse = null;
    this.location.back();
  }

  setSex(m: string) {
    console.log('setSex()');
    this.horse.sex = m;
  }
  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  /**
   * Adds a new horse to the database
   */
  createHorse() {
    console.log('Horse:');
    console.log(this.horse);
    /*this.horse.description = '';
    this.horse.favSport = -1; //default values
    this.horseService.createHorse(this.horse).subscribe(
      (horse: Horse) => {
        this.horse = horse;
        this.goBack();
        this.resetHorse();
        //todo: show success message
      },
      error => {
        //todo: add specific error message for user (in defaultServiceErrorHandlingMetod)
        this.defaultServiceErrorHandling(error);
      }
    );*/
  }

  private defaultServiceErrorHandling(error: any) {
    console.log(error);
    this.error = true;
    if (error.status === 0) {
      // If status is 0, the backend is probably down
      this.errorMessage = 'The backend seems not to be reachable';
    } else if (error.error.message === 'No message available') {
      // If no detailed error message is provided, fall back to the simple error name
      this.errorMessage = error.error.error;
    } else {
      this.errorMessage = error.error.message;
    }
  }

  /**
   * @private Resets the HorseObject so old values do not remain in the form
   */
  private resetHorse() {
    this.horse = new Horse();
  }

  /**
   * resets name of the current horse to null
   */
  resetName(){
    this.horse.name=null;
  }

  /**
   * resets description of the current horse to null
   */
  resetDescription() {
    this.horse.description=null;
  }

  resetFavSport() {
    this.horse.favSport=null;
  }
}
