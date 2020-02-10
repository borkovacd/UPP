import { Component, OnInit } from '@angular/core';
import {TextProcessingService} from '../../../service/text-processing.service';
import {RepositoryService} from '../../../service/repository.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-define-correction-time',
  templateUrl: './define-correction-time.component.html',
  styleUrls: ['./define-correction-time.component.css']
})
export class DefineCorrectionTimeComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private enumValues = [];

  constructor(private textProcessingService: TextProcessingService,
              private repositoryService: RepositoryService,
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
      if(value[property]=="PT10M" || value[property]=="PT5M" || value[property]=="PT3M") {
        o.push({fieldId: property, fieldValue: value[property]});
        console.log(o);
        let x = this.textProcessingService.setCorrectionTime(o, this.formFieldsDto.taskId);

        const processInstanceId = this.route.snapshot.params.processInstanceId;

        x.subscribe(
          res => {
            alert("Zadato je vreme za ispravku");
            //this.router.navigateByUrl('/text-information/' + processInstanceId);
          },
          err => {
            console.log("Error occured");
          }
        );
      } else {
        alert("Vrednost nije odgovarajuceg formata! Pokusajte ponovo!")
      }

    }

  }
}

