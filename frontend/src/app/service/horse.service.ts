import { Injectable } from '@angular/core';
import {Horse} from '../dto/horse';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from 'src/environments/environment';
import {TreeHorse} from '../dto/TreeHorse';


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
    console.log('Create new horse', horse);
    return this.httpClient.post<Horse>(
      baseUri,
      horse
    );
  }

  /**
   * Fetches all horses from the backend.
   */
  getAllHorses(): Observable<Horse[]> {
    console.log('Load all horses ' + baseUri);
    return this.httpClient.get<Horse[]>(baseUri);
  }

  /**
   * Fetches all horses from the db and loads their fields id, name, dateOfbirth, parent1Id, parent2Id
   *
   * @return list of all horses in TreeHorse format
   */
  getAllTreeHorses(): Observable<TreeHorse[]> {
    console.log('Load all horses for tree ' + baseUri + '/bloodline');
    return this.httpClient.get<TreeHorse[]>(baseUri + '/bloodline');
  }

  /**
   * Loads specific horse from the backend
   *
   * @param id of horse to load
   */
  getHorseById(id: number | string): Observable<Horse> {
    console.log('Load horse details for ' + id);
    return this.httpClient.get<Horse>(baseUri + '/' + id);
  }

  /**
   * Updates an existing instance of horse
   */
  updateHorse(horse: Horse): Observable<any> {
    console.log('Update horse with id ' + horse.id);
    return this.httpClient.put<Horse>(baseUri, horse);
  }

  /**
   * queries the DB for horses that match the fields set in searchHorse
   *
   * @param searchHorse bundled fields that will be used in the query
   * @return a list of horses that match the criteria of searchHorse
   */
  searchHorses(searchHorse: Horse): Observable<Horse[]> {
    /*let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json');*/
    console.log(searchHorse);
    let params = new HttpParams();
    if (searchHorse.name != null){
      params = params.set('name', searchHorse.name);
    }
    if (searchHorse.sex != null){
      params = params.set('sex', searchHorse.sex);
    }
    if (searchHorse.dateOfBirth != null){
      params = params.set('dateOfBirth', searchHorse.dateOfBirth);
    }
    if (searchHorse.description != null){
      params = params.set('description', searchHorse.description);
    }
    if (searchHorse.favSportId != null){
      params = params.set('favSportId', searchHorse.favSportId.toString());
    }
    console.log(params.toString());

    return this.httpClient.get<Horse[]>(baseUri + '/?', {params: <any>searchHorse}); //todo: ask tutor what to use instead of <any>
  }

  /**
   * deletes horse and all its parent-child relationships from the db
   *
   * @param id of the horse to be deleted
   */
  deleteHorse(id: number) {
    console.log('Delete horse with id ' + id);
    return this.httpClient.delete<any>(baseUri + '/' + id);
  }
}
