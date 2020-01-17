import { Component, OnInit } from '@angular/core';
import {UserService} from '../../../service/user.service';
import {RepositoryService} from '../../../service/repository.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-magazine-final-page',
  templateUrl: './magazine-final-page.component.html',
  styleUrls: ['./magazine-final-page.component.css']
})
export class MagazineFinalPageComponent implements OnInit {


  constructor(private userService : UserService,
              private repositoryService : RepositoryService,
              public router: Router,
              private route: ActivatedRoute) {

  }

  ngOnInit() {

  }

}
