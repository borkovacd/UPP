import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TextProcessingService {

  constructor(private httpClient: HttpClient) {
  }

  startTextProcessingProcess() : Observable<any> {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.httpClient.get('http://localhost:8080/textProcessing/start');
  }

  getTaskForm(processInstanceId){
    return this.httpClient.get('http://localhost:8080/textProcessing/getTaskForm/'.concat(processInstanceId)) as Observable<any>
  }

  decideOnRegistration(registration, taskId) {
    return this.httpClient.post("http://localhost:8080/textProcessing/decideOnRegistration/".concat(taskId), registration) as Observable<any>;
  }
}
