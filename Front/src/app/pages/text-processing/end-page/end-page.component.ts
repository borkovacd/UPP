import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-end-page',
  templateUrl: './end-page.component.html',
  styleUrls: ['./end-page.component.css']
})
export class EndPageComponent implements OnInit {

  private loggedUser = null;


  constructor() { }

  ngOnInit() {
    this.loggedUser = localStorage.getItem("USERNAME");
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

