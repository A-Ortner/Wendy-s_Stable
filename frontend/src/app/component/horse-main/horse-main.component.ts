import { Component, OnInit } from '@angular/core';
import { Horse} from '../../dto/horse';
import { HorseService} from '../../service/horse.service';

@Component({
  selector: 'app-horse-main',
  templateUrl: './horse-main.component.html',
  styleUrls: ['./horse-main.component.scss']
})
export class HorseMainComponent implements OnInit {

  error = false;
  errorMessage = '';
  horses: Horse[];

  constructor(private horseService: HorseService) {
  }

  ngOnInit(): void {
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

}
