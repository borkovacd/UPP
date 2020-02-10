import { Component, OnInit } from '@angular/core';
import {TextProcessingService} from '../../../service/text-processing.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FormSubmissionWithFileDto} from '../../../model/FormSubmissionWithFileDto';

@Component({
  selector: 'app-article-correction-after-review-again',
  templateUrl: './article-correction-after-review-again.component.html',
  styleUrls: ['./article-correction-after-review-again.component.css']
})
export class ArticleCorrectionAfterReviewAgainComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processInstanceId = null;

  private fileField = null;
  private fileName = null;

  constructor(private textProcessingService: TextProcessingService,
              public router: Router,
              private route: ActivatedRoute) {

  }

  ngOnInit() {

    const taskId = this.route.snapshot.params.taskId;
    const x = this.textProcessingService.loadTask(taskId);

    x.subscribe(
      res => {
        console.log(res);
        console.log('Ispis rezultata');
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        console.log(this.formFields);
        this.processInstanceId = res.processInstanceId;
      },
      err => {
        console.log('Error occured');
      }
    );
  }

  fileChoserListener(files: FileList, field){
    let fileToUpload = files.item(0);
    field.fileName = files.item(0).name;
    this.fileName = files.item(0).name;

    let fileReader = new FileReader();

    fileReader.onload = (e) => {

      field.value = fileReader.result;
      this.fileField = fileReader.result;
      console.log(fileReader.result);
    }

    fileReader.readAsDataURL(files.item(0))
  }

  onSubmit(value, form) {
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId: property, fieldValue: value[property]});
    }

    console.log(o);
    let y = new FormSubmissionWithFileDto(o, this.fileField.toString(), this.fileName.toString());
    let x = this.textProcessingService.updateArticleData(y, this.formFieldsDto.taskId);


    x.subscribe(
      res => {
        console.log("Article corrected: "  + res);
        if (res == true) {
          alert("Uspesno ste korigovali rad");
          this.router.navigateByUrl('/user-informing');
        } else {
          alert("Rad nije uspesno korigovan");
          this.router.navigateByUrl('/article-final');
        }
      },
      err => {
        console.log("Error occured");
      }
    );
  }
}


