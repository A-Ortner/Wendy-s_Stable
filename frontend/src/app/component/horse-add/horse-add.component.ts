import {Component, OnInit} from '@angular/core';
import {Horse} from '../../dto/horse';
import {HorseService} from '../../service/horse.service';
import {Location} from '@angular/common';
import {NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import {Sport} from '../../dto/sport';
import {SportService} from '../../service/sport.service';


@Component({
  selector: 'app-horse-add',
  templateUrl: './horse-add.component.html',
  styleUrls: ['./horse-add.component.scss']
})
export class HorseAddComponent implements OnInit {

  error = false;
  errorMessage = '';
  dateError: string;

  horse: Horse;
  horses: Horse[];
  sports: Sport[];

  model: NgbDateStruct;

  constructor(private horseService: HorseService,
              private location: Location,
              private sportService: SportService) {
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
    console.log('createHorse()');

    this.horseService.createHorse(this.horse).subscribe(
      (horse: Horse) => {
        this.horse = horse;
        alert('Created ' + this.horse.name);
        this.goBack();
        this.resetHorse();
      },
      error => {
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

    if (this.horse.dateOfBirth == null) {
      this.dateError = 'Date of birth must be set.';
      return false;
    }
    return true;
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
