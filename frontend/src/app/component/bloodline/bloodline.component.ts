import {Component, OnInit} from '@angular/core';
import {Horse} from '../../dto/horse';
import {Location} from '@angular/common';
import {TreeHorse} from '../../dto/TreeHorse';
import {HorseService} from '../../service/horse.service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-bloodline',
  templateUrl: './bloodline.component.html',
  styleUrls: ['./bloodline.component.scss']
})
export class BloodlineComponent implements OnInit {

  error = false;
  errorMessage = '';
  horses: TreeHorse[];
  ancestors: TreeHorse[];

  rootId: string;
  rid: number;
  generations: string;
  genNum: number;

  constructor(private location: Location,
              private horseService: HorseService,
              private router: Router) {
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
    console.log(!isNaN(Number(this.generations)));
    if (this.generations === null) {
      return false;
    }
    if(this.generations.includes('.') || this.generations.includes(',')){
      return false;
    }
    if(isNaN(Number(this.generations))){
      return false;
    }
    if(Number(this.generations)<1){
      return false;
    }
    return true;
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
        this.loadAllTreeHorses();
        this.loadTree();
      },
      error => {
        alert('Could not delete horse ' + name + '.');
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  /**
   * goes to details page of specified id
   *
   * @param id of the horse who´s details are to be shown
   */
  goToDetails(id: number) {
    this.router.navigate(['/details-horse/' + id]);
  }


  loadTree() {
    //Reset tree
    const tree = document.getElementById('tree-container');
    while (tree.firstChild) {
      tree.removeChild(tree.firstChild);
    }

    //do nothing if fields not set
    if (this.rootId == null || !this.isNumber()) {
      return;
    }

    //save id of root horse and #generations
    this.rid = Number(this.rootId);
    this.genNum = Number(this.generations);

    //load ancestors of roothorse
    this.horseService.getBloodline(this.rid, this.genNum).subscribe((horses: TreeHorse[]) => {
        this.ancestors = horses;
        const rootHorse = this.ancestors.find(x => x.id === this.rid);
        const horsesDecimated = this.ancestors.filter(x => x.id !== this.rid);

        this.createFieldsRec(rootHorse, 0, horsesDecimated);
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

  private loadAllTreeHorses() {
    this.horseService.getAllTreeHorses().subscribe(
      (horses: Horse[]) => {
        this.horses = horses;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  private createFieldsRec(currHorse: TreeHorse, level: number, horses: TreeHorse[]) {

    const tree = document.getElementById('tree-container');
    const element = document.createElement('div');
    element.textContent = currHorse.name + ' ' + currHorse.dateOfBirth.toString();
    const offset = level * 50;
    element.style.marginLeft = offset.toString() + 'px';
    element.className = 'form-control';
    element.id = currHorse.id.toString();
    element.style.alignContent='center';
    element.style.alignItems = 'center';
    element.style.marginBottom='5px';

    //details button
    const detBtn = document.createElement('button');
    detBtn.type = 'button';
    //detBtn.className = 'btn btn-light btn-outline-secondary';
    detBtn.style.alignContent = 'center';
    detBtn.textContent = 'DETAILS';
    detBtn.addEventListener('click', (e) => this.goToDetails(currHorse.id));

    //detBtn.appendChild(routeLink);
    console.log(detBtn);
    console.log(element);
    element.appendChild(detBtn);

    //delete button
    const delBtn = document.createElement('button');
    delBtn.style.margin = '5px';
    delBtn.type = 'button';
    //delBtn.className = 'btn btn-outline-secondary';
    delBtn.textContent = 'DELETE';
    delBtn.addEventListener('click', (e) => this.deleteHorse(currHorse.id, currHorse.name));

    element.appendChild(delBtn);
    //make children expandable
    /*if (level === 0) {
      element.hidden = false;
    } else {
      element.hidden = true;
    }
    element.onclick(this.expandChildren(element.id));*/
    tree.appendChild(element);

    const horsesDecimated = horses.filter(x => x.id !== this.rid);

    if (currHorse.parent1Id !== null) {
      const parent1 = horses.find(x => x.id === currHorse.parent1Id);
      this.createFieldsRec(parent1, level + 1, horsesDecimated);
    }

    if (currHorse.parent2Id !== null) {
      const parent2 = horses.find(x => x.id === currHorse.parent2Id);
      this.createFieldsRec(parent2, level + 1, horsesDecimated);
    }
  }


}
