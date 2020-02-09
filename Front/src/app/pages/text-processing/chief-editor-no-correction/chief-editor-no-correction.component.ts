import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-chief-editor-no-correction',
  templateUrl: './chief-editor-no-correction.component.html',
  styleUrls: ['./chief-editor-no-correction.component.css']
})
export class ChiefEditorNoCorrectionComponent implements OnInit {

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
