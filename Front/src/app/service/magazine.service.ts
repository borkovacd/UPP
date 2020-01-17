import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MagazineService {

  constructor(private httpClient: HttpClient) {
  }

  registerMagazine(magazine, taskId) {
    return this.httpClient.post("http://localhost:8080/magazine/create/".concat(taskId), magazine) as Observable<any>;
  }

  registerMagazineSANumber(num, taskId) {
    return this.httpClient.post("http://localhost:8080/magazine/postMSAnumber/".concat(taskId), num) as Observable<any>;
  }

  registerMagazineSAName(num, taskId) {
    return this.httpClient.post("http://localhost:8080/magazine/postMSAname/".concat(taskId), num) as Observable<any>;
  }


}
