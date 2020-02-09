import { Component, OnInit } from '@angular/core';
import {TextProcessingService} from '../../../service/text-processing.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-pdf-review',
  templateUrl: './pdf-review.component.html',
  styleUrls: ['./pdf-review.component.css']
})
export class PdfReviewComponent implements OnInit {
  private formFieldsDto = null;
  private formFields = [];
  private enumValues = [];

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
    let x = this.textProcessingService.decideOnPDF(o, this.formFieldsDto.taskId);

    const processInstanceId = this.route.snapshot.params.processInstanceId;

    x.subscribe(
      res => {
        console.log("Correction needed: "  + res);
        if (res == false) {
          alert("Odlucili ste da nije potrebna korekcija dokumenta");
          //this.router.navigateByUrl('/login/' + processInstanceId);
        } else {
          alert("Odlucili ste da je potrebna korekcija dokumenta");
          this.router.navigateByUrl('/chief-editor-informing');
        }
      },
      err => {
        console.log("Error occured");
      }
    );
  }
}
