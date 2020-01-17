import { Component, OnInit } from '@angular/core';
import {UserService} from '../../service/user.service';
import {RepositoryService} from '../../service/repository.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-confirm-reviewer-page',
  templateUrl: './confirm-reviewer-page.component.html',
  styleUrls: ['./confirm-reviewer-page.component.css']
})
export class ConfirmReviewerPageComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private enumValues = [];

  private processIsFinished = null;



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
        if(res.formFields == null) {
          this.processIsFinished = true;
        } else {
          this.processIsFinished = false;
          this.formFieldsDto = res;
          this.formFields = res.formFields;
          this.formFields.forEach((field) => {

            if (field.type.name == 'enum') {
              this.enumValues = Object.keys(field.type.values);
            }
          });
        }
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
    let x = this.userService.registerStatus(o, this.formFieldsDto.taskId);

    const processInstanceId = this.route.snapshot.params.processInstanceId;

    x.subscribe(
      res => {
        console.log(res);
        alert("Uspesno ste odredili status korisnika")
        location.reload();
      },
      err => {
        console.log("Error occured");
      }
    );
  }

}
