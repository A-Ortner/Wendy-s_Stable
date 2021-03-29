import { Component, OnInit } from '@angular/core';
import {Sport} from '../../dto/sport';
import {Location} from '@angular/common';
import {SportService} from '../../service/sport.service';

@Component({
  selector: 'app-sport-add',
  templateUrl: './sport-add.component.html',
  styleUrls: ['./sport-add.component.scss']
})
export class SportAddComponent implements OnInit {

  error = false;
  errorMessage = '';
  sport: Sport;

  constructor(private location: Location,
              private sportService: SportService,) { }

  ngOnInit(): void {
    this.sport = new Sport();
  }

  /**
   * Sets location to previous page
   */
  goBack() {
    this.sport = null;
    this.location.back();
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  /**
   * Adds a new sport to the database
   */
  createSport() {
    this.sportService.createSport(this.sport).subscribe(
      (sport: Sport) => {
        this.sport = sport;
        alert('Created ' + this.sport.name);
        this.goBack();
        this.sport = null;}
    );
  }
}
