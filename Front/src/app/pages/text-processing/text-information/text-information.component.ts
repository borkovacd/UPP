import { Component, OnInit } from '@angular/core';
import {TextProcessingService} from '../../../service/text-processing.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FormSubmissionWithFileDto} from '../../../model/FormSubmissionWithFileDto';
import {MagazineService} from '../../../service/magazine.service';

@Component({
  selector: 'app-text-information',
  templateUrl: './text-information.component.html',
  styleUrls: ['./text-information.component.css']
})
export class TextInformationComponent implements OnInit {

  private formSubmissionWithFile  = null;
  private formFieldsDto = null;
  private formFields = [];
  private processInstance = "";
  private enumValues = [];
  private tasks = [];
  private claimedTaskId = null;

  private fileField = null;
  private fileName = null;

  private number = null;

  private areas = [];

  constructor(private textProcessingService: TextProcessingService,
              private magazineService: MagazineService,
              public router: Router,
              private route: ActivatedRoute) {

  }

  ngOnInit() {

    const processInstanceId = this.route.snapshot.params.processInstanceId;
    const x = this.textProcessingService.getTaskFormArticleInformation(processInstanceId);
    x.subscribe(
      res => {
        console.log(res);
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        console.log(this.formFields);
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

      if(property.toString() == "broj_koautora") {
        this.number = value[property];
      }
      if(property.toString() == "pdf") {
        value[property] = this.fileName;
      }
    }
    for (const property in value) {

      if (property != 'naucne_oblasti') {
        o.push({fieldId : property, fieldValue : value[property]});
      } else {
        o.push({fieldId : property, categories : value[property]});

      }
      console.log('niz za slanje izgleda');
      console.log(o);
    }


    console.log(o);
    let y = new FormSubmissionWithFileDto(o, this.fileField.toString(), this.fileName.toString());
    let x = this.textProcessingService.postArticleData(y, this.formFieldsDto.taskId);

    const processInstanceId = this.route.snapshot.params.processInstanceId;

    x.subscribe(
      res => {
        alert("Osnovni podaci o radu su uspešno uneti");
        this.router.navigateByUrl('coauthor-data/' + processInstanceId + '/' + this.number);
      },
      err => {
        console.log("Error occured");
      }
    );
  }
}
