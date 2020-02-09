import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-chief-editor-informing',
  templateUrl: './chief-editor-informing.component.html',
  styleUrls: ['./chief-editor-informing.component.css']
})
export class ChiefEditorInformingComponent implements OnInit {

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
