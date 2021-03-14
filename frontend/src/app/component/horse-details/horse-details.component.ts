import { Component, OnInit } from '@angular/core';
import {Horse} from '../../dto/horse';
import {HorseService} from '../../service/horse.service';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';

@Component({
  selector: 'app-horse-details',
  templateUrl: './horse-details.component.html',
  styleUrls: ['./horse-details.component.scss']
})
export class HorseDetailsComponent implements OnInit {
  horse: Horse;

  constructor(private horseService: HorseService,
              private route: ActivatedRoute,
              private location: Location) { }

  ngOnInit(): void {
    this.getHorse();
  }

  getHorse(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.horseService.getHorseById(id)
      .subscribe(horse => this.horse = horse);
  }

  /**
   * Sets location to previous page
   */
  goBack() {
    this.horse = null;
    this.location.back();
  }
}
