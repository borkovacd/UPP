import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-review-done',
  templateUrl: './review-done.component.html',
  styleUrls: ['./review-done.component.css']
})
export class ReviewDoneComponent implements OnInit {


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
