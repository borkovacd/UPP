import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {RepositoryService} from "../../services/repository/repository.service";

@Component({
  selector: 'app-welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.css']
})
export class WelcomePageComponent implements OnInit {

  private processInstance = "";

  constructor(public router: Router, private repositoryService : RepositoryService) {

  }

  ngOnInit() {

  }


  registration() {

    this.repositoryService.startNewProcess().subscribe(data => {
      console.log(data);
      this.processInstance = data.processInstanceId;
      this.router.navigateByUrl('/registration/' + this.processInstance);
    })

  }

}
