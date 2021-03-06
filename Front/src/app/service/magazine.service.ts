import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MagazineService {

  constructor(private httpClient: HttpClient) {
  }

  getAllMagazines() {
    return this.httpClient.get('http://localhost:8080/magazine/getAllMagazines') as Observable<any>;
  }

  getAllScientificAreas() {
    return this.httpClient.get('http://localhost:8080/magazine/getAllScientificAreas') as Observable<any>;
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

  checkMagazine(num, taskId) {
    return this.httpClient.post("http://localhost:8080/magazine/checkData/".concat(taskId), num) as Observable<any>;
  }

  setActivationForMagazine(num, taskId) {
    return this.httpClient.post("http://localhost:8080/magazine/setActivation/".concat(taskId), num) as Observable<any>;
  }

  updateMagazine(magazine, taskId) {
    console.log('update magazine');
    console.log(magazine);
    return this.httpClient.post('http://localhost:8080/magazine/update/'.concat(taskId), magazine) as Observable<any>;
  }




}
