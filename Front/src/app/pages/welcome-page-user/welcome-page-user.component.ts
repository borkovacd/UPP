import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {RepositoryService} from '../../services/repository/repository.service';
import {UserService} from '../../services/users/user.service';

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

    this.repositoryService.startProcessCreateMagazine().subscribe(data => {
      console.log(data);
      this.processInstance = data.processInstanceId;
      this.router.navigateByUrl('/magazineCreation/' + this.processInstance);
    })

  }

}
