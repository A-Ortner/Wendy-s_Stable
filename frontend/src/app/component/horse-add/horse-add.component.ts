import {Component, OnInit} from '@angular/core';
import {Horse} from '../../dto/horse';
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
  dateError: string;
  parent1Error: string;
  parent2Error: string;

  horse: Horse;
  horses: Horse[];
  parent1: Horse;
  parent2: Horse;
  sports: Sport[];

  model: NgbDateStruct;

  constructor(private horseService: HorseService,
              private location: Location,
              private sportService: SportService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.horse = new Horse();
    this.horse.dateOfBirth=null;
    this.horse.parent1Id=null;
    this.horse.parent2Id=null;

    this.getAllSports();
    this.getAllHorses();
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
  resetName() {
    this.horse.name = null;
  }

  /**
   * resets description of the current horse to null
   */
  resetDescription() {
    this.horse.description = null;
  }

  resetFavSport() {
    this.horse.favSportId = null;
  }

  /**
   * checks if dateOfBirth is not null
   */
  dateValid() {
    console.log('check date');

    //check if date is not in future
    //check children: parents cannot be born after children
    if (this.horse.dateOfBirth == null) {
      this.dateError = 'Date of birth must be set.';
      return false;
    }
    return true;
  }

  /**
   * checks if
   * (1) parents are not of the same sex
   * (2) dateOfBirth of parent1 is before set dateOfBirth
   * (3) dateOfBirth of parent2 is before set dateOfBirth
   */
 /* parentsValid() {
    console.log('check parents');
    console.log(this.horse);

    this.parent1Error = '';
    this.parent2Error = '';

    //same sex
    if (this.horse.parent1Id !== null && this.horse.parent2Id !== null) {
      this.parent1 = this.horses.filter(x => x.id === this.horse.parent1Id)[0];
      this.parent2 = this.horses1.filter(x => x.id === this.horse.parent2Id)[0];
      console.log(this.parent1);
      console.log(this.parent2);

      if (this.parent1.sex === this.parent2.sex) {
        this.parent1Error += 'The horse´s parents cannot be of the same sex.';
        this.parent2Error += 'The horse´s parents cannot be of the same sex.';
      }
    }

    //date parent1
    if (this.horse.parent1Id != null) {
      console.log('checking date1 for pID ' + this.horse.parent1Id);
      console.log(this.horses);
      this.parent1 = this.horses.filter(x => x.id === this.horse.parent1Id)[0];
      console.log(this.parent1);

      if ((this.horse.dateOfBirth !== null)) {
        if (this.cmpAge(this.horse.dateOfBirth, this.parent1.dateOfBirth) === 1) {
          //this horse is older than parent2
          this.parent1Error += ' Parent 1 cannot be younger than the horse you want to add.';
        }
      }
    }

    //date parent2
    if (this.horse.parent2Id !== null) {
      console.log('checking date2 for pID ' + this.horse.parent2Id);
      this.parent2 = this.horses.filter(x => x.id === this.horse.parent2Id)[0];
      console.log(this.parent2);
      if ((this.horse.dateOfBirth !== null)) {
        if (this.cmpAge(this.horse.dateOfBirth, this.parent2.dateOfBirth) === 1) {
          //this horse is older than parent2
          this.parent2Error += ' Parent 2 cannot be younger than the horse you want to add.';
        }
      }
    }

    if ((this.parent1Error !== '') || (this.parent2Error !== '')) {
      return false;
    }
    return true;
  }*/

  /**
   * compares 2 date
   *
   * @param date1 date to be compared to date2
   * @param date2 date to be compared to date1
   * @private
   * @return 0 if equal,
   *         1 if date2 is after date1,
   *         -1 if date1 is before date2
   */
  private cmpAge(date1: string, date2: string): number {
    console.log('cmp age');
    const dateOne = new Date(date1);
    const dateTwo = new Date(date1);

    if (dateOne < dateTwo) {return -1;}
    else if (dateOne > dateTwo) {return 1;}
    else if (dateTwo === dateOne) {return 0;}
    else{alert('cannot compare dates');}
  }

  /**
   * loads all sports from the db
   */
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
   * loads all horses from the db
   */
  private getAllHorses() {
    this.horseService.getAllHorses().subscribe(
      (horses: Horse[]) => {
        this.horses = horses;
        console.log(this.horses);
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
