import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../../service/user.service';
import {MagazineService} from '../../../service/magazine.service';
import {RepositoryService} from '../../../service/repository.service';
import {TextProcessingService} from '../../../service/text-processing.service';

@Component({
  selector: 'app-choosing-reviewers',
  templateUrl: './choosing-reviewers.component.html',
  styleUrls: ['./choosing-reviewers.component.css']
})
export class ChoosingReviewersComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private reviewers = [];
  private processInstanceId = null;
  private enumValues = [];


  constructor(private route: ActivatedRoute,
              public router: Router,
              private userService: UserService,
              private magazineService: MagazineService,
              private repositoryService: RepositoryService,
              private textProcessingService: TextProcessingService) {


  }

  ngOnInit() {
    const taskId = this.route.snapshot.params.taskId;
    const x = this.textProcessingService.getTaskFormChoosingReviewers(taskId);

    x.subscribe(
      res => {
        console.log(res);
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        console.log(this.formFields);
        this.processInstanceId = res.processInstanceId;
        this.formFields.forEach( (field) =>{

          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
          }
        });



      },
      err => {
        console.log('Error occured');
      }
    );

  }

  onSubmit(value, form){
    console.log(form);
    console.log(value);
    const o = new Array();

    for (const property in value) {

      if (property != 'izborRecenzenata') {
        o.push({fieldId : property, fieldValue : value[property]});
      } else {
        o.push({fieldId : property, categories : value[property]});

      }
      console.log('niz za slanje izgleda');
      console.log(o);
    }


    let x = this.textProcessingService.chooseReviewers(o, this.formFieldsDto.taskId);
    //const taskId = this.route.snapshot.params.taskId;

    const taskId = this.route.snapshot.params.taskId;

    x.subscribe(
      res => {
        console.log(res);
        if(res==false) {
          alert("Nije izabran odgovarajuć broj recenzenata. Pokušajte ponovo");
          //this.router.navigateByUrl('text-information/' + processInstanceId);
        } else {
          alert("Izabran je odgovarajuć broj recenzenata.");
          this.router.navigateByUrl('review-time/' + this.processInstanceId);

        }
      },
      err => {
        console.log("Error occured");
      }
    );
  }

  filterByScientificArea() {
    const taskId = this.route.snapshot.params.taskId;
    const x = this.textProcessingService.getTaskFormChoosingReviewersFiltered(taskId);

    x.subscribe(
      res => {
        console.log(res);
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        console.log(this.formFields);
        this.formFields.forEach((field) => {

          if (field.type.name == 'enum') {
            this.enumValues = Object.keys(field.type.values);
          }
        });


      },
      err => {
        console.log('Error occured');
      }
    );
  }
}
