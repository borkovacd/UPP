import { Component, OnInit } from '@angular/core';
import {UserService} from '../../../service/user.service';
import {RepositoryService} from '../../../service/repository.service';
import {ActivatedRoute, Router, RouterModule} from "@angular/router";

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css']
})
export class RegistrationPageComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processInstance = "";
  private enumValues = [];
  private tasks = [];
  private claimedTaskId = null;

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
        //this.processInstance = res.processInstanceId;
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
    let x = this.userService.registerUser(o, this.formFieldsDto.taskId);

    const processInstanceId = this.route.snapshot.params.processInstanceId;

    x.subscribe(
      res => {
        console.log(res);
        if(res==false) {
          alert("Registracija uspešno izvršena!");
          this.router.navigateByUrl('scientific-area/' + processInstanceId);
        } else {
          alert("Nije uspešno izvršena registracija");
          location.reload();
        }
      },
      err => {
        console.log("Error occured");
      }
    );
  }

  /*getTasks(){
    const processInstanceId = this.route.snapshot.params.processInstanceId;
    let x = this.repositoryService.getTasks(processInstanceId);

    x.subscribe(
      res => {
        console.log(res);
        this.tasks = res;
      },
      err => {
        console.log("Error occured");
      }
    );
  }*/

  /*claim(taskId){
    let x = this.repositoryService.claimTask(taskId);

    x.subscribe(
      res => {
        console.log(res);


        this.claimedTaskId = taskId;

        let y = this.repositoryService.getNewTask(taskId);

        y.subscribe(
          res => {
            console.log(res);
            this.formFieldsDto = res;
            this.formFields = res.formFields;
            //this.processInstance = res.processInstanceId;
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

      },
      err => {
        console.log("Error occured");
      }
    );
  }

  complete(taskId){
    const processInstanceId = this.route.snapshot.params.processInstanceId;
    let x = this.repositoryService.completeTask(processInstanceId,taskId);

    x.subscribe(
      res => {
        console.log(res);
        this.tasks = res;
      },
      err => {
        console.log("Error occured");
      }
    );
  }*/

}
