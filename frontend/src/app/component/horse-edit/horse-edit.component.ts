import {Component, OnInit} from '@angular/core';
import {Horse} from '../../dto/horse';
import {Location} from '@angular/common';
import {ActivatedRoute, Router} from '@angular/router';
import {HorseService} from '../../service/horse.service';
import {Sport} from '../../dto/sport';
import {SportService} from '../../service/sport.service';
import {timer} from 'rxjs';


@Component({
  selector: 'app-horse-edit',
  templateUrl: './horse-edit.component.html',
  styleUrls: ['./horse-edit.component.scss']
})
export class HorseEditComponent implements OnInit {

  horse: Horse;
  error = false;
  errorMessage = '';
  sports: Sport[];
  dateError: string;
  sport: Sport;

  constructor(private location: Location,
              private route: ActivatedRoute,
              private horseService: HorseService,
              private sportService: SportService,
              private router: Router) {
  }

  async ngOnInit() {
    this.getHorseAndSport();
    this.getAllSports();
  }

  /**
   * Sets location to previous page
   */
  goBack() {
    this.horse = null;
    this.location.back();
  }

  /**
   * Update an existing horse in the database and navigates back to main
   *
   * @param horse contains all set values
   */
  updateHorse() {
    this.horseService.updateHorse(this.horse).subscribe(
      (horse: Horse) => {
        this.horse = horse;
        alert('Updated ' + this.horse.name);
        this.router.navigate(['/horses']);
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }


  dateValid() {
    //check if date set
    //check if date is not in future
    //check children: parents cannot be born after children
    if (this.horse.dateOfBirth == null) {
      this.dateError = 'Date of birth must be set.';
      return false;
    }
    return true;
    //todo: check if in future in backend and for parents and propagate it to this error!
  }

  /**
   * load the horse with the ID specified in URL
   */
  private getHorseAndSport(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.horseService.getHorseById(id)
      .subscribe(horse => {
        this.horse = horse;
        console.log('horse and sport:');
        console.log(this.horse);
        this.getSportById(this.horse.favSportId);
      });

  }


  /**
   * load all sports
   */
  private getAllSports() {
    this.sportService.getAllSports().subscribe(
      (sports: Sport[]) => {
        this.sports = sports;
        console.log(this.sports);
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  private getSportById(id: number) {
    this.sportService.getSportById(id).subscribe(
      (sport: Sport) => {
        this.sport = sport;
        console.log(sport); //todo: remove
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
