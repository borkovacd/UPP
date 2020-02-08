import { Component, OnInit } from '@angular/core';
import {MagazineService} from '../../../service/magazine.service';
import {RepositoryService} from '../../../service/repository.service';
import {ActivatedRoute, Router} from '@angular/router';
import {TextProcessingService} from '../../../service/text-processing.service';

@Component({
  selector: 'app-need-registration',
  templateUrl: './need-registration.component.html',
  styleUrls: ['./need-registration.component.css']
})
export class NeedRegistrationComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processInstance = "";
  private enumValues = [];
  private tasks = [];
  private claimedTaskId = null;

  constructor(private textProcessingService: TextProcessingService,
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
        this.formFields.forEach((field) => {

          if (field.type.name == 'enum') {
            this.enumValues = Object.keys(field.type.values);
          }
        });
      },
      err => {
        console.log("Error occured");
      }
    );

  }

  onSubmit(value, form) {
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId: property, fieldValue: value[property]});
    }

    console.log(o);
    let x = this.textProcessingService.decideOnRegistration(o, this.formFieldsDto.taskId);

    const processInstanceId = this.route.snapshot.params.processInstanceId;

    x.subscribe(
      res => {
        console.log("Registration needed: "  + res);
        if (res == false) {
          alert("Odlucili ste da Vam nije potrebna registracija");
          this.router.navigateByUrl('/login/' + processInstanceId);
        } else {
          alert("Odlucili ste da Vam je potrebna registracija");
          this.router.navigateByUrl('/registration/' + processInstanceId);
        }
      },
      err => {
        console.log("Error occured");
      }
    );
  }
}
