import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {RepositoryService} from '../../service/repository.service';
import {UserService} from '../../service/user.service';

@Component({
  selector: 'app-welcome-page-user',
  templateUrl: './welcome-page-user.component.html',
  styleUrls: ['./welcome-page-user.component.css']
})
export class WelcomePageUserComponent implements OnInit {

  private processInstance = "";
  private loggedUser = null;
  private username = null;
  private userType = null;

  constructor(public router: Router,
              private repositoryService : RepositoryService,
              private userService: UserService) {

  }

  ngOnInit() {

    this.loggedUser = sessionStorage.getItem("loggedUser");
    this.username = JSON.parse(window.sessionStorage.getItem('loggedUser')).username;
    this.userType = JSON.parse(window.sessionStorage.getItem('loggedUser')).userType;


  }


  createMagazine() {

    this.repositoryService.startProcessCreateMagazine(this.username).subscribe(data => {
      console.log(data);
      this.processInstance = data.processInstanceId;
      this.router.navigateByUrl('/magazineCreation/' + this.processInstance);
    })

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
