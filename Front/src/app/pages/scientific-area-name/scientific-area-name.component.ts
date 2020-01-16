import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/users/user.service";
import {RepositoryService} from "../../services/repository/repository.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-scientific-area-name',
  templateUrl: './scientific-area-name.component.html',
  styleUrls: ['./scientific-area-name.component.css']
})
export class ScientificAreaNameComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private enumValues = [];
  private numberTemp = null;


  constructor(private userService : UserService,
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

  ngOnChanges() {

    const numberTemp = this.route.snapshot.params.number;
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
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);
    let x = this.userService.registerScientificAreaName(o, this.formFieldsDto.taskId);

    const processInstanceId = this.route.snapshot.params.processInstanceId;

    x.subscribe(
      res => {
        console.log(res);
        if(res==false) {
          alert("Uspešno ste dodali naučnu oblast");
          this.numberTemp = this.route.snapshot.params.number;
          this.numberTemp = this.numberTemp - 1;
          console.log("NUMBER TEMP JE (van petlje) : " + this.numberTemp)
          if(this.numberTemp > 0) {
            console.log("NUMBER TEMP JE: " + this.numberTemp)
            this.router.navigateByUrl('scientific-area-name/' + processInstanceId + '/' + this.numberTemp);
            this.ngOnInit();
          } else {
            this.router.navigateByUrl('/activation-page/' + processInstanceId + '/' + 0);
          }
        } else {
          alert("Nije uspešno dodata naučna oblast");
          location.reload();
        }

      },
      err => {
        console.log("Error occured");
      }
    );
  }

}
