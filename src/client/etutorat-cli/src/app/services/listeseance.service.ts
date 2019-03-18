import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Token } from '../responseBodies/token';


@Injectable({
  providedIn: 'root'
})
export class ListeseanceService {

  constructor(private http : HttpClient) { }

  findAllSeanceTutore(listSeanceForm)
  {
    return this.http.post<Token>(environment.API_URL+"/seance/seancetutore", listSeanceForm);
  }
  findAllSeanceTuteur(listSeanceForm)
  {
    return this.http.post<Token>(environment.API_URL+"/seance/seancetuteur", listSeanceForm);

  }

  findAllSeances()
  {
    return this.http.get<Token>(environment.API_URL+"/seance/all")

  }
}
