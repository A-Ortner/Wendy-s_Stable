import {Component, Input, OnChanges, OnInit, Output} from '@angular/core';
import {TreeHorse} from '../../dto/TreeHorse';
import {EventEmitter } from '@angular/core';


@Component({
  selector: 'app-tree-element',
  templateUrl: './tree-element.component.html',
  styleUrls: ['./tree-element.component.scss']
})
export class TreeElementComponent implements OnInit, OnChanges {

  @Input()
  ancestors: TreeHorse[];

  @Input()
  currentHorse: TreeHorse;

  @Output() myEvent: EventEmitter<number> = new EventEmitter<number>();

  parents: TreeHorse[];
  remainingAncestors: TreeHorse[];
  showChildElements: boolean;
  private error: boolean;
  private errorMessage: string;

  constructor() {
  }

  ngOnInit(): void {
    this.showChildElements = false;
    this.setParentsAndRemainingAncestors();
  }

  ngOnChanges() {
    this.ngOnInit();
  }

  toggleShow() {
    this.showChildElements = !this.showChildElements;
  }

  /**
   * emits call to parent-components
   *
   * @param id of the horse to be deleted
   */
  deleteHorse(id: number) {
    this.showChildElements = false;
    this.myEvent.emit(id);
  }

  /**
   * re-throws event until bloodline-component is reached
   *
   * @param id id of the horse to be deleted
   */
  catchDelete(id: number) {
    this.myEvent.emit(id);
  }

  /**
   * applies filter to the loaded horses to filter out the direct parents of the current horse
   * and then removes current horse from the list of horses
   *
   * @private method
   */
  private setParentsAndRemainingAncestors() {
    this.parents = this.ancestors.filter(x => x.id === this.currentHorse.parent1Id || x.id === this.currentHorse.parent2Id);
    this.remainingAncestors = this.ancestors.filter(x => x.id !== this.currentHorse.id);
  }

}
