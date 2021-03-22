import {Component, OnInit} from '@angular/core';
import {Horse} from '../../dto/horse';
import {Location} from '@angular/common';
import {TreeHorse} from '../../dto/TreeHorse';
import {HorseService} from '../../service/horse.service';
import {Observable} from 'rxjs';



@Component({
  selector: 'app-bloodline',
  templateUrl: './bloodline.component.html',
  styleUrls: ['./bloodline.component.scss']
})
export class BloodlineComponent implements OnInit {

  error = false;
  errorMessage = '';
  horses: TreeHorse[];
  horses$: Observable<TreeHorse[]>;
  horse$: Observable<TreeHorse>;
  rootId: number;
  generations: number;

  constructor(private location: Location,
              private horseService: HorseService) {
  }

  ngOnInit(): void {
    this.loadAllTreeHorses();
  }

  /**
   * Sets location to previous page
   */
  goBack() {
    this.location.back();
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  /**
   * checks if the string set in generations is a number
   */
  isNumber(): boolean {
    console.log(this.generations);
    if(this.generations === null) {return false;}
    return !isNaN(Number(this.generations));

  }


  loadTree(rootId: number) {
    console.log(rootId);
    const rootHorse = this.horses.filter((item) => this.horses.map((horse) => horse.id === rootId))[0];
    console.log(rootHorse);
    const horsesDecimated = this.horses.filter((item) => this.horses.map((horse) => horse.id !== rootId));
    this.createFieldsRec(rootHorse, 0, horsesDecimated);

   /* this.horse$.pipe (
      map(items =>
        items.filter(item => item.name.toLowerCase().indexOf(query) > -1)) );*/
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

  private loadAllTreeHorses() {
    this.horseService.getAllTreeHorses().subscribe(
      (horses: Horse[]) => {
        this.horses = horses;
        console.log(this.horses);
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  private createFieldsRec(currHorse: TreeHorse, level: number, horses: TreeHorse[]) {
    console.log('level ' + level);
    console.log('horse ' + currHorse.name);
    const tree = document.getElementById('tree-container');
    const element = document.createElement('div');
    element.textContent= currHorse.name + ' ' + currHorse.dateOfBirth.toString() + ' ' + level;
    tree.appendChild(element);

    const horsesDecimated = horses.filter((item) => horses.map((horse) => horse.id !== currHorse.id));
    const parent1 = horses.filter((item) => horses.map((horse) => horse.id === currHorse.parent1Id));
    console.log('horses decimated: ' + horsesDecimated);
    console.log('parent1');
    console.log(parent1[0]);


    if(parent1.length === 1){
      this.createFieldsRec(parent1[0], level+1, horsesDecimated);
    }

    const parent2 = this.horses.filter((item) => this.horses.map((horse) => horse.id === currHorse.parent2Id));
    console.log('parent2 ');
    console.log(parent2[0]);
    if(parent2.length === 1){
      this.createFieldsRec(parent2[0], level+1, horsesDecimated);
    }
  }
}
