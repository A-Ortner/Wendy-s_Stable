import {Component, OnInit} from '@angular/core';
import {Horse} from '../../dto/horse';
import {HorseService} from '../../service/horse.service';
import {Sport} from '../../dto/sport';
import {SportService} from '../../service/sport.service';

@Component({
  selector: 'app-horse-main',
  templateUrl: './horse-main.component.html',
  styleUrls: ['./horse-main.component.scss']
})
export class HorseMainComponent implements OnInit {

  error = false;
  errorMessage = '';
  horses: Horse[];
  searchHorse: Horse;
  sports: Sport[];

  constructor(private horseService: HorseService,
              private sportService: SportService) {
  }

  ngOnInit(): void {
    this.searchHorse = new Horse();
    this.getAllHorses();
    this.getAllSports();
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  /**
   * loads all horses from the backend that match the fields set in searchHorse
   */
  searchHorses() {
    this.horseService.searchHorses(this.searchHorse).subscribe(
      (horses: Horse[]) => {
        this.horses = horses;
      },
      error => {
        //todo: ask if ok
        this.horses = [];
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  resetDate() {
    this.searchHorse.dateOfBirth = null;
  }

  /**
   * Filters sport from sports according to id
   *
   * @param favSportId
   */
  getSport(favSportId: number) {
    if (favSportId == null) {
      return '';
    } else {
      return this.sports.filter(s => s.id = favSportId)[0].name;
    }
  }

  /**
   * deletes horse from db and parent-child relationships
   *
   * @param id of the horse to be deleted
   * @param name: name of the horse to be deleted
   */
  deleteHorse(id: number, name: string) {
    this.horseService.deleteHorse(id).subscribe(() => {
        alert('Deleted horse ' + name + '.');
        this.getAllHorses();
      },
      error => {
        alert('Could not delete horse ' + name + '.');
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  private getAllHorses() {
    this.horseService.getAllHorses().subscribe(
      (horses: Horse[]) => {
        this.horses = horses;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
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
