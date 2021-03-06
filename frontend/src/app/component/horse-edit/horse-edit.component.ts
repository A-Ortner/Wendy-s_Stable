import {Component, OnInit} from '@angular/core';
import {Horse} from '../../dto/horse';
import {Location} from '@angular/common';
import {ActivatedRoute, Router} from '@angular/router';
import {HorseService} from '../../service/horse.service';
import {Sport} from '../../dto/sport';
import {SportService} from '../../service/sport.service';


@Component({
  selector: 'app-horse-edit',
  templateUrl: './horse-edit.component.html',
  styleUrls: ['./horse-edit.component.scss']
})
export class HorseEditComponent implements OnInit {

  error = false;
  errorMessage = '';
  dateError: string;

  sports: Sport[];
  sport: Sport;
  sportname: string;
  horses: Horse[];
  horse: Horse;
  parent1: Horse;
  parent2: Horse;

  constructor(private location: Location,
              private route: ActivatedRoute,
              private horseService: HorseService,
              private sportService: SportService,
              private router: Router) {
  }

  async ngOnInit() {
    this.sportname = 'none';
    this.getAllFields();
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
   */
  updateHorse() {
    console.log(this.horse);
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
    if (this.horse.dateOfBirth == null) {
      this.dateError = 'Date of birth must be set.';
      return false;
    }
    return true;
  }

  /**
   * loads a list of all horses from the DB, then filters the horse with the ID specified in URL out
   * loads a list of all sports from the DB, then filters the sport with the ID specified in horse.favSportId
   */
  private getAllFields() {
    this.horseService.getAllHorses()
      .subscribe(horses => {
          this.horses = horses;
          const id = +this.route.snapshot.paramMap.get('id');
          this.horse = this.horses.filter(h => h.id === id)[0];
        console.log(this.horse);
          if(this.horse.parent1Id!=null){
            this.parent1 = this.horses.filter(p => p.id === this.horse.parent1Id)[0];
          }

          if(this.horse.parent2Id != null){
            this.parent2 = this.horses.filter( p => p.id === this.horse.parent2Id)[0];
          }


          this.sportService.getAllSports().subscribe(
            (sports: Sport[]) => {
              this.sports = sports;
              if(this.horse.favSportId != null) {
                this.sport = this.sports.filter(s => s.id === this.horse.favSportId)[0];
                this.sportname = this.sport.name;
              }
            },
            error => {
              this.defaultServiceErrorHandling(error);
            }
          );
        }, error => {
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
