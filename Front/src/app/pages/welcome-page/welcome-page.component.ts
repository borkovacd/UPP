import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {RepositoryService} from '../../service/repository.service';
import {UserService} from '../../service/user.service';
import {TextProcessingService} from '../../service/text-processing.service';

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
              private userService: UserService,
              private textProcessingService: TextProcessingService) {

  }

  ngOnInit() {

    this.loggedUser = localStorage.getItem("USERNAME");
  }


  registration() {

    this.repositoryService.startNewProcess().subscribe(data => {
      console.log(data);
      this.processInstance = data.processInstanceId;
      this.router.navigateByUrl('/registration/' + this.processInstance);
    })

  }

  submitNewText() {
    this.textProcessingService.startTextProcessingProcess().subscribe(data => {
      console.log(data);
      this.processInstance = data.processInstanceId;

      this.userService.userIsLoggedIn().subscribe( data => {
        console.log("There is a logged in user " + data);
        if(data == false) {
          this.router.navigateByUrl('/need-registration/' + this.processInstance);
        } else {
          this.router.navigateByUrl('/choosing-magazine/' + this.processInstance);
        }
      })
    })
  }

  logIn() {
    this.router.navigateByUrl('/login');
  }

  logOut() {
    /*let x =  this.userService.logoutUser();
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
    );*/
    localStorage.clear();
    alert('Successfully logged out!');
    window.location.href = '';
  }
}
