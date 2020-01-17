import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RepositoryService} from '../../../service/repository.service';
import {MagazineService} from '../../../service/magazine.service';

@Component({
  selector: 'app-magazine-scientific-area-page',
  templateUrl: './magazine-scientific-area-page.component.html',
  styleUrls: ['./magazine-scientific-area-page.component.css']
})
export class MagazineScientificAreaPageComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private enumValues = [];

  private number = null;


  constructor(private magazineService: MagazineService,
              private repositoryService : RepositoryService,
              public router: Router,
              private route: ActivatedRoute) {

  }

  ngOnInit() {

    const processInstanceId = this.route.snapshot.params.processInstanceId;
    let x = this.repositoryService.getNewTask(processInstanceId);

    x.subscribe(
      res => {
        console.log(res);
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.formFields.forEach( (field) =>{

          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
          }
        });
      },
      err => {
        console.log("Error occured");
      }
    );

  }

  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      this.number = value[property];
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);
    let x = this.magazineService.registerMagazineSANumber(o, this.formFieldsDto.taskId);

    const processInstanceId = this.route.snapshot.params.processInstanceId;

    x.subscribe(
      res => {
        console.log(res);
        alert("Uspesno ste dodali željeni broj naučnih oblasti časopisa!")
        this.router.navigateByUrl('magazine-scientific-area-name/' + processInstanceId + '/' + this.number);
      },
      err => {
        console.log("Error occured");
      }
    );
  }

}
