import { Component, OnInit } from '@angular/core';
import {TextProcessingService} from '../../../service/text-processing.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-assigned-editor-reviewing-corrected',
  templateUrl: './assigned-editor-reviewing-corrected.component.html',
  styleUrls: ['./assigned-editor-reviewing-corrected.component.css']
})
export class AssignedEditorReviewingCorrectedComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processInstanceId = null;

  private enumValues = [];


  constructor(private textProcessingService: TextProcessingService,
              public router: Router,
              private route: ActivatedRoute) {

  }

  ngOnInit() {

    const taskId = this.route.snapshot.params.taskId;
    const x = this.textProcessingService.getTaskFormWithFinalDecisions(taskId);

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

      if (property != 'konacnaOdluka') {
        o.push({fieldId : property, fieldValue : value[property]});
      } else {
        o.push({fieldId : property, categories : value[property]});

      }
      console.log('niz za slanje izgleda');
      console.log(o);
    }

    let x = this.textProcessingService.assignedEditorReviewCorrected(o, this.formFieldsDto.taskId);


    x.subscribe(
      res => {
        console.log("Proces se zavrsava -> " + res);
        if (res == true) {
          this.router.navigateByUrl('/article-final');
        } else {
          this.router.navigateByUrl('chief-editor-informing');
        }
      },
      err => {
        console.log("Error occured");
      }
    );
  }
}

