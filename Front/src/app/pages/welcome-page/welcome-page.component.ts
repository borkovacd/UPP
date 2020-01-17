import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {RepositoryService} from '../../service/repository.service';
import {UserService} from '../../service/user.service';

@Component({
  selector: 'app-welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.css']
})
export class WelcomePageComponent implements OnInit {

  private processInstance = "";
  private loggedUser = null;

  constructor(public router: Router,
              private repositoryService : RepositoryService,
              private userService: UserService) {

  }

  ngOnInit() {

    this.loggedUser = sessionStorage.getItem("loggedUser");

  }


  registration() {

    this.repositoryService.startNewProcess().subscribe(data => {
      console.log(data);
      this.processInstance = data.processInstanceId;
      this.router.navigateByUrl('/registration/' + this.processInstance);
    })

  }

  logIn() {
    this.router.navigateByUrl('/login');
  }

  logOut() {
    let x =  this.userService.logoutUser();
    x.subscribe(
      res => {
        alert('Successfully logged out!');
        sessionStorage.clear();
       //sessionStorage.setItem('loggedUser', null);
        window.location.href = '';
      },
      err => {
        console.log('Mistake!');
      }
    );
  }
}
