import {Component, OnInit} from '@angular/core';
import {Horse} from '../../dto/horse';
import {HorseService} from '../../service/horse.service';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';
import {Sport} from '../../dto/sport';
import {SportService} from '../../service/sport.service';

@Component({
  selector: 'app-horse-details',
  templateUrl: './horse-details.component.html',
  styleUrls: ['./horse-details.component.scss']
})
export class HorseDetailsComponent implements OnInit {
  horse: Horse;
  sport: Sport;

  constructor(private horseService: HorseService,
              private route: ActivatedRoute,
              private location: Location,
              private sportService: SportService) {
  }

  ngOnInit(): void {
    this.getHorseAndSport();
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

  private getSportById(id: number) {
    this.sportService.getSportById(id).subscribe(
      (sport: Sport) => {
        this.sport = sport;
        console.log(sport); //todo: remove
      });
  }
}
