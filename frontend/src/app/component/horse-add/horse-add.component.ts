import {Component, OnInit} from '@angular/core';
import { Horse } from '../../dto/horse';
import {HorseService} from '../../service/horse.service';
import {Location} from '@angular/common';
import {NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import {Sport} from '../../dto/sport';
import {SportService} from '../../service/sport.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-horse-add',
  templateUrl: './horse-add.component.html',
  styleUrls: ['./horse-add.component.scss']
})
export class HorseAddComponent implements OnInit {

  error = false;
  errorMessage = '';
  horse: Horse;
  model: NgbDateStruct;
  sports: Sport[];
  dateError: string;
  dateOfBirth: Date ;

  constructor(private horseService: HorseService,
              private location: Location,
              private sportService: SportService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.horse = new Horse();
    this.getAllSports();
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
    if(this.horse.description == null) {this.horse.description = '';}
    if(this.horse.favSportId == null) {this.horse.favSportId = -1;} //default values
    this.horseService.createHorse(this.horse).subscribe(
      (horse: Horse) => {
        this.horse = horse;
        alert('Created ' + this.horse.name);
        this.goBack();
        this.resetHorse();
      },
      error => {
        //todo: add specific error message for user (in defaultServiceErrorHandlingMethod)
        this.defaultServiceErrorHandling(error);
      }
    );
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
    this.horse.favSportId=null;
  }

  dateValid() {
    //check if date set
    //check if date is not in future
    //check children: parents cannot be born after children
    if(this.horse.dateOfBirth == null){
      this.dateError = 'Date of birth must be set.';
      return false;
    }
    return true;
    //todo: check if in future in backend and for parents and propagate it to this error!
  }

  private getAllSports() {
    this.sportService.getAllSports().subscribe(
      (sports: Sport[]) => {
        this.sports = sports;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  /**
   * @private Resets the HorseObject so old values do not remain in the form
   */
  private resetHorse() {
    this.horse = new Horse();
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


}
