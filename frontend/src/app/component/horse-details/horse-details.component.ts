import {Component, OnInit} from '@angular/core';
import {Horse} from '../../dto/horse';
import {HorseService} from '../../service/horse.service';
import {ActivatedRoute, ActivatedRouteSnapshot, Router} from '@angular/router';
import {Location} from '@angular/common';
import {Sport} from '../../dto/sport';
import {SportService} from '../../service/sport.service';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-horse-details',
  templateUrl: './horse-details.component.html',
  styleUrls: ['./horse-details.component.scss']
})
export class HorseDetailsComponent implements OnInit {

  error = false;
  errorMessage = '';

  horses: Horse[];
  horse: Horse;
  horse$: Observable<Horse>;
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

    /*this.horse$ = this.route.paramMap.pipe(
      switchMap((params: ParamMap) =>
        this.horseService.getHorseById(params.get('id')))
    );*/

    this.getAllFields();
  }

  /**
   * load the horse with the ID specified in URL
   */
  getHorseAndSport(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.horseService.getHorseById(id)
      .subscribe(horse => {
        this.horse = horse;
        console.log('horse and sport:');
        console.log(this.horse);//todo: remove
        this.getSportById(this.horse.favSportId);
      });
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
    this.router.routeReuseStrategy.shouldReuseRoute =(future: ActivatedRouteSnapshot, curr: ActivatedRouteSnapshot)=> {
      console.log(future.url.toString());
      console.log(curr.url.toString());
      if (future.url.toString() === 'view' && curr.url.toString() === future.url.toString()) {
        return false;
      }
      return (future.routeConfig === curr.routeConfig);
    };
    this.router.navigate(['/details-horse/' + id]);
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
   * loads a list of all horses from the DB, then filters the horse with the ID specified in URL out
   * loads a list of all sports from the DB, then filters the sport with the ID specified in horse.favSportId
   */
  private getAllFields() {
    this.horseService.getAllHorses()
      .subscribe(horses => {
          console.log('get all horses');
          this.horses = horses;

          const id = +this.route.snapshot.paramMap.get('id');
          this.horse = this.horses.filter(h => h.id === id)[0];
          console.log(this.horse);
          if (this.horse.parent1Id != null) {
            this.parent1 = this.horses.filter(p => p.id === this.horse.parent1Id)[0];
            this.parent1name = this.parent1.name;
          }

          if (this.horse.parent2Id != null) {
            this.parent2 = this.horses.filter(p => p.id === this.horse.parent2Id)[0];
            this.parent2name = this.parent2.name;
          }

          if (this.horse.favSportId !== null) {
            this.sportService.getSportById(id).subscribe(
              (sport: Sport) => {
                this.sport = sport;
                this.sportname = sport.name;
              },
              error => {
                this.defaultServiceErrorHandling(error);
              }
            );
          }
        }, error => {
          this.defaultServiceErrorHandling(error);
        }
      );
  }

  private getSportById(id: number) {
    this.sportService.getSportById(id).subscribe(
      (sport: Sport) => {
        this.sport = sport;
        console.log(sport); //todo: remove
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
