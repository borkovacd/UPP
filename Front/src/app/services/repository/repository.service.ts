import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import { Observable } from 'rxjs';
import {convertRuleOptions} from "tslint/lib/configuration";

@Injectable({
  providedIn: 'root'
})
export class RepositoryService {

  constructor(private httpClient: HttpClient) {


  }

  startNewProcess() : Observable<any> {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.httpClient.get('http://localhost:8080/welcome/getNewProcess');
  }

  startProcessCreateMagazine() : Observable<any> {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.httpClient.get('http://localhost:8080/magazine/startCreateMagazineProcess');
  }

  getNewTask(processInstanceId){
    return this.httpClient.get('http://localhost:8080/welcome/getNewTask/'.concat(processInstanceId)) as Observable<any>
  }

  getNewerTask(processInstanceId){
    return this.httpClient.get('http://localhost:8080/welcome/getNewerTask/'.concat(processInstanceId)) as Observable<any>
  }

  getTasks(processInstance : string){
    return this.httpClient.get('http://localhost:8080/welcome/get/tasks/'.concat(processInstance)) as Observable<any>
  }


  claimTask(taskId){
    return this.httpClient.post('http://localhost:8080/welcome/tasks/claim/'.concat(taskId), null) as Observable<any>
  }

  completeTask(processInstanceId, taskId){
    return this.httpClient.get(`http://localhost:8080/welcome/tasks/complete/${processInstanceId}/${taskId}`) as Observable<any>
  }

  confirmationOfApplication(processInstanceId) {
    return this.httpClient.get('http://localhost:8080/welcome/confirmationOfApplication/'.concat(processInstanceId)) as Observable<any>
  }
}
