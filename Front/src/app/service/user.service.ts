import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  loginUser(login) {
    console.log(login);
    return this.httpClient.post('http://localhost:8080/users/loginUser', login) as Observable<any>;
  }

  loginAdmin(login) {
    console.log(login);
    return this.httpClient.post('http://localhost:8080/users/loginAdmin', login) as Observable<any>;
  }

  logoutUser() {
    console.log('U logout je');
    return this.httpClient.get('http://localhost:8080/users/logout') as Observable<any>;
  }

  registerUser(user, taskId) {
    return this.httpClient.post("http://localhost:8080/welcome/post/".concat(taskId), user) as Observable<any>;
  }

  registerNumber(num, taskId) {
    return this.httpClient.post("http://localhost:8080/welcome/postNumber/".concat(taskId), num) as Observable<any>;
  }

  registerStatus(status, taskId) {
    return this.httpClient.post("http://localhost:8080/welcome/postStatus/".concat(taskId), status) as Observable<any>;
  }

  registerScientificAreaName(num, taskId) {
    return this.httpClient.post("http://localhost:8080/welcome/postScientificAreaName/".concat(taskId), num) as Observable<any>;
  }

  next(o: any[], taskId: any) {
    return this.httpClient.post("http://localhost:8080/welcome/postSomething/".concat(taskId), o) as Observable<any>;
  }

  getReviewers() {
    return this.httpClient.get('http://localhost:8080/users/getReviewers') as Observable<any>;
  }
  getEditors() {
    return this.httpClient.get('http://localhost:8080/users/getEditors') as Observable<any>;
  }

  userIsLoggedIn() {
    return this.httpClient.get('http://localhost:8080/users/loggedIn') as Observable<any>;
  }
  getTasksOfUser(username: string) {
    console.log('u gettasksofuser ' + username);
    return this.httpClient.get('http://localhost:8080/users/getTasksUser/'.concat(username)) as Observable<any>;
  }

  startTask(task) {
    console.log(task.name);
    console.log(task.taskId);
    if (task.name === 'Pregled rada') {
      window.location.href = 'article-review/' + task.taskId;
    } else if (task.name === 'Korigovanje prijave (PDF dokumenta)') {
      window.location.href = 'article-correction/' + task.taskId;
    } else if (task.name === 'Izbor recenzenata') {
      window.location.href = 'choosing-reviewers/' + task.taskId;
    } else if (task.name === 'Recenziranje') {
      window.location.href = 'chief-editor-reviewing/' + task.taskId;
    } else {
      alert("U pitanju je neki drugi task!")
    }
  }

  getAllMagazineReviewers(processInstanceId) {
    return this.httpClient.get('http://localhost:8080/users/getAllMagazineReviewers/'.concat(processInstanceId)) as Observable<any>;
  }

  getFilteredMagazineReviewers(processInstanceId) {
    return this.httpClient.get('http://localhost:8080/users/getFilteredMagazineReviewers/'.concat(processInstanceId)) as Observable<any>;
  }

}
