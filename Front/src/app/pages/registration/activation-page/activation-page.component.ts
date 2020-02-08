import { Component, OnInit } from '@angular/core';
import {UserService} from '../../../service/user.service';
import {RepositoryService} from '../../../service/repository.service';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-activation-page',
  templateUrl: './activation-page.component.html',
  styleUrls: ['./activation-page.component.css']
})
export class ActivationPageComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private enumValues = [];
  private numberTemp = null;
  private continue = false;


  constructor(private userService : UserService,
              private repositoryService : RepositoryService,
              public router: Router,
              private route: ActivatedRoute) {

  }

  ngOnInit() {

    const processInstanceId = this.route.snapshot.params.processInstanceId;
    this.numberTemp = this.route.snapshot.params.number;
    let x = this.repositoryService.getNewerTask(processInstanceId);

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

    if(this.numberTemp == 1) {
      let y = this.repositoryService.confirmationOfApplication(processInstanceId);
      y.subscribe(
        res => {
          console.log(res);
          if(res == true) {
            this.continue = true;
          } else {
            this.continue = false;
          }

        },
        err => {
          console.log("Error occured");
        }
      );
    }

  }


  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    const processInstanceId = this.route.snapshot.params.processInstanceId;

    console.log(o);
    let x = this.userService.next(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);
        this.router.navigateByUrl('/login/0/' + processInstanceId);
      },
      err => {
        console.log("Error occured");
      }
    );
  }

}
