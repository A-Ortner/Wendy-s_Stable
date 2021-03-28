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
  showRoot: boolean;

  private rootHorse: TreeHorse;


  constructor(private location: Location,
              private horseService: HorseService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.generations = null;
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
    if (this.generations === null) {
      return false;
    }
    if (this.generations.includes('.') || this.generations.includes(',')) {
      return false;
    }
    if (isNaN(Number(this.generations))) {
      return false;
    }
    if (Number(this.generations) < 1) {
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
    console.log(' delete ' + id);
    this.horseService.deleteHorse(id).subscribe(() => {
        alert('Deleted horse ' + name + '.');
        if(this.rid === id){
          this.showRoot = false;
          this.rootId = null;
          this.loadAllTreeHorses();
        }else{
          this.loadAllTreeHorses();
          this.loadTree();
        }
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


  catchDelete(event: number) {
    console.log('catch delete' + event);
    const toDelete = this.horses.find(x => x.id === event);
    console.log(toDelete);
    this.deleteHorse(event, toDelete.name);
  }

  loadTree() {
    console.log('loadTree()');
    //Reset tree
    /* const tree = document.getElementById('tree-container');
     while (tree!== null && tree.firstChild) {
       tree.removeChild(tree.firstChild);
     }*/

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
        this.rootHorse = this.ancestors.find(x => x.id === this.rid);
        this.showRoot = true;
      }, error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }


  private defaultServiceErrorHandling(error: any) {
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
    console.log('loadAllTreeHorses');
    this.horseService.getAllTreeHorses().subscribe(
      (horses: Horse[]) => {
        this.horses = horses;
        console.log(horses);
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }
}
