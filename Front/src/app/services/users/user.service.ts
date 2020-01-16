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

  registerScientificAreaName(num, taskId) {
    return this.httpClient.post("http://localhost:8080/welcome/postScientificAreaName/".concat(taskId), num) as Observable<any>;
  }

  next(o: any[], taskId: any) {
    return this.httpClient.post("http://localhost:8080/welcome/postSomething/".concat(taskId), o) as Observable<any>;
  }
}
