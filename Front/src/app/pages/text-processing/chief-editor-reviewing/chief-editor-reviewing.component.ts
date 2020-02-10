import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../../service/user.service';
import {MagazineService} from '../../../service/magazine.service';
import {RepositoryService} from '../../../service/repository.service';
import {TextProcessingService} from '../../../service/text-processing.service';
import {FormSubmissionWithFileDto} from '../../../model/FormSubmissionWithFileDto';

@Component({
  selector: 'app-chief-editor-reviewing',
  templateUrl: './chief-editor-reviewing.component.html',
  styleUrls: ['./chief-editor-reviewing.component.css']
})
export class ChiefEditorReviewingComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processInstanceId = null;

  private fileField = null;
  private fileName = null;

  private enumValues = [];


  constructor(private textProcessingService: TextProcessingService,
              public router: Router,
              private route: ActivatedRoute) {

  }

  ngOnInit() {

    const taskId = this.route.snapshot.params.taskId;
    const x = this.textProcessingService.getTaskFormWithDecisions(taskId);

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

      if (property != 'odluka_GU') {
        o.push({fieldId : property, fieldValue : value[property]});
      } else {
        o.push({fieldId : property, categories : value[property]});

      }
      console.log('niz za slanje izgleda');
      console.log(o);
    }

    let x = this.textProcessingService.chiefEditorReview(o, this.formFieldsDto.taskId);


    x.subscribe(
      res => {
        console.log("Proces se zavrsava -> " + res);
        if (res == true) {
          this.router.navigateByUrl('/article-final');
        } else {
          this.router.navigateByUrl('correction-time/' + this.processInstanceId);
        }
      },
      err => {
        console.log("Error occured");
      }
    );
  }
}
