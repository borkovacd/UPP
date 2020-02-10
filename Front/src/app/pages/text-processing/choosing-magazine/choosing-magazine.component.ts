import { Component, OnInit } from '@angular/core';
import {TextProcessingService} from '../../../service/text-processing.service';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../../service/user.service';
import {MagazineService} from '../../../service/magazine.service';
import {RepositoryService} from '../../../service/repository.service';

@Component({
  selector: 'app-choosing-magazine',
  templateUrl: './choosing-magazine.component.html',
  styleUrls: ['./choosing-magazine.component.css']
})
export class ChoosingMagazineComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processId = '';
  private username = '';
  private magazines = [];
  private enumValues = [];

  constructor(private route: ActivatedRoute,
              public router: Router,
              private userService: UserService,
              private magazineService: MagazineService,
              private repositoryService: RepositoryService,
              private textProcessingService: TextProcessingService) {

    this.route.params.subscribe( params => {this.processId = params.processInstanceId; });

    const x = textProcessingService.getTaskFormMagazinesChoosing(this.processId);
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

  ngOnInit() {
  }

  onSubmit(value, form){
    console.log(form);
    console.log(value);
    const o = new Array();

    for (const property in value) {

      if (property != 'casopisi') {
        o.push({fieldId : property, fieldValue : value[property]});
      } else {
        o.push({fieldId : property, categories : value[property]});

      }
      console.log('niz za slanje izgleda');
      console.log(o);
    }

    const processInstanceId = this.route.snapshot.params.processInstanceId;

    let x = this.textProcessingService.chooseMagazine(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log("Potrebno placanje -> " + res);
        if(res==false) {
          alert("Nije potrebno uplatiti članarinu!");
          this.router.navigateByUrl('text-information/' + processInstanceId);
        } else {
          alert("Neophodna je uplata članarine!");
          this.router.navigateByUrl('membership-payment/' + processInstanceId );
        }
      },
      err => {
        console.log("Error occured");
      }
    );
  }

}
