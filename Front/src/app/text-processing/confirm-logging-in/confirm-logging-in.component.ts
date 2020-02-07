import { Component, OnInit } from '@angular/core';
import {UserService} from '../../service/user.service';
import {RepositoryService} from '../../service/repository.service';
import {ActivatedRoute, Router} from '@angular/router';
import {TextProcessingService} from '../../service/text-processing.service';

@Component({
  selector: 'app-confirm-logging-in',
  templateUrl: './confirm-logging-in.component.html',
  styleUrls: ['./confirm-logging-in.component.css']
})
export class ConfirmLoggingInComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private enumValues = [];


  constructor(private userService : UserService,
              private repositoryService : RepositoryService,
              private textProcessingService: TextProcessingService,
              public router: Router,
              private route: ActivatedRoute) {

  }

  ngOnInit() {

    const processInstanceId = this.route.snapshot.params.processInstanceId;

    let x = this.textProcessingService.getTaskForm(processInstanceId);

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

    const processInstanceId = this.route.snapshot.params.processInstanceId;

    console.log(o);
    let x = this.userService.next(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);
        this.router.navigateByUrl('/choosing-magazine/' + processInstanceId);
      },
      err => {
        console.log("Error occured");
      }
    );
  }

}
