import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material/dialog';
import {HorseAddComponent} from '../horse-add/horse-add.component';
import {Horse} from '../../dto/horse';


@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss']
})
export class DialogComponent{

  constructor(private dialog: MatDialog) {}

  openDialog() {

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = { horse : Horse};

    this.dialog.open(HorseAddComponent, dialogConfig);
  }

}
