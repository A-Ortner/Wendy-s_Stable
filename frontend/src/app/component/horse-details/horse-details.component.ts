import {Component, OnInit} from '@angular/core';
import {Horse} from '../../dto/horse';
import {HorseService} from '../../service/horse.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {Location} from '@angular/common';
import {Sport} from '../../dto/sport';
import {SportService} from '../../service/sport.service';
import {Observable} from 'rxjs';
import {switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-horse-details',
  templateUrl: './horse-details.component.html',
  styleUrls: ['./horse-details.component.scss']
})
export class HorseDetailsComponent implements OnInit {

  error = false;
  errorMessage = '';

  horse: Horse;
  horses$: Observable<Horse[]>;
  parent1: Horse;
  parent1name: string;
  parent2: Horse;
  parent2name: string;
  sport: Sport;
  sportname: string;

  constructor(private horseService: HorseService,
              private route: ActivatedRoute,
              private router: Router,
              private location: Location,
              private sportService: SportService) {
  }

  ngOnInit(): void {
    this.sportname = 'none';
    this.parent1name = 'none';
    this.parent2name = 'none';

    this.horses$ = this.route.paramMap.pipe(
      switchMap((params: ParamMap) =>
        this.horseService.getFullHorse(Number(params.get('id'))))
    );
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
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  goToDetails(id: number) {
    this.router.navigate(['/details-horse/', id]);
  }

  /**
   * deletes horse from db and parent-child relationships
   *
   * @param id of the horse to be deleted
   */
  deleteHorse(id: number) {
    this.horseService.deleteHorse(id).subscribe(() => {
        alert('Deleted horse ' + this.horse.name + '.');
        this.goToMain();
      },
      error => {
        alert('Could not delete horse ' + this.horse.name + '.');
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  /**
   * load the specified horse and its parents from the db
   * if favSportId is set for horse, the corresponding sport will be fetched from the db
   */
  private getAllFields() {
    this.horses$.subscribe(horses => {
      this.parent1 = null;
      this.parent1name = 'none';
      this.parent2 = null;
      this.parent2name = 'none';

      const id = +this.route.snapshot.paramMap.get('id');
      this.horse = horses.find(x => x.id === id);
      console.log(this.horse);
      if (this.horse.parent1Id !== null) {
        console.log(this.horse.parent1Id);
        this.parent1 = horses.find(x => x.id === Number(this.horse.parent1Id));
        this.parent1name = this.parent1.name;
      }

      if (this.horse.parent2Id !== null) {
        this.parent2 = horses.find(x => x.id === this.horse.parent2Id);
        this.parent2name = this.parent2.name;
      }

      if (this.horse.favSportId !== null) {
        this.sportService.getSportById(this.horse.favSportId).subscribe(
          (sport: Sport) => {
            console.log(sport);
            this.sport = sport;
            this.sportname = sport.name;
          },
          error => {
            this.defaultServiceErrorHandling(error);
          }
        );
      }
    });
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

  private goToMain() {
    this.router.navigate(['/horses']);
  }
}
