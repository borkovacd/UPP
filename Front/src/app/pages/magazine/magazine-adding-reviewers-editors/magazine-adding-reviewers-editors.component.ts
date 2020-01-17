import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../../service/user.service';
import {MagazineService} from '../../../service/magazine.service';
import {RepositoryService} from '../../../service/repository.service';

@Component({
  selector: 'app-magazine-adding-reviewers-editors',
  templateUrl: './magazine-adding-reviewers-editors.component.html',
  styleUrls: ['./magazine-adding-reviewers-editors.component.css']
})
export class MagazineAddingReviewersEditorsComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processId = '';
  private username = '';
  private reviewers = [];
  private editors = [];

  constructor(private route: ActivatedRoute,
              public router: Router,
              private userService: UserService,
              private magazineService: MagazineService,
              private repositoryService: RepositoryService) {
    this.route.params.subscribe( params => {this.processId = params.processInstanceId; });

    const x = repositoryService.getNewTask(this.processId);
    x.subscribe(
      res => {
        console.log(res);
        console.log('Ispis rezultata');
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        console.log(this.formFields);
        this.userService.getReviewers().subscribe(
          pom => {
            console.log('Ispis recenzenata');
            console.log(pom);
            this.reviewers = pom;
          }
        );
        this.userService.getEditors().subscribe(
          red => {
            console.log('Ispis editora');
            console.log(red);
            this.editors = red;
          }
        );
      },
      err => {
        console.log('Error occured');
      }
    );

  }

  ngOnInit() {
  }

  onSubmit(value, form) {
    console.log(form);
    console.log(value);
    const o = new Array();

    for (const property in value) {

      if (property != 'recenzentiMagazina' && property != 'uredniciMagazina' ) {
        o.push({fieldId : property, fieldValue : value[property]});
      } else {
        o.push({fieldId : property, categories : value[property]});

      }
      console.log('niz za slanje izgleda');
      console.log(o);
    }

    const processInstanceId = this.route.snapshot.params.processInstanceId;

    let x = this.magazineService.updateMagazine(o, this.formFieldsDto.taskId);
    console.log('Pre subscribe');
    x.subscribe(
      res => {
        console.log(res);
        alert('You  have successfully updated magazine!');
        this.router.navigateByUrl('/login/1/' + processInstanceId);
      },
      err => {
        console.log('Error occured');
      }
    );
  }
}
