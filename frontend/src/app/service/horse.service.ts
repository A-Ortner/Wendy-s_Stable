import { Injectable } from '@angular/core';
import {Horse} from '../dto/horse';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from 'src/environments/environment';


const baseUri = environment.backendUrl + '/horses';

@Injectable({
  providedIn: 'root'
})
export class HorseService {

  constructor(private httpClient: HttpClient) { }

  /**
   * Creates a new horse in the database
   *
   * @param horse: horse to be added
   */
  createHorse(horse: Horse) {
    console.log('Create new sport', horse);
    return this.httpClient.post<Horse>(
      baseUri,
      horse
    );
  }
}
