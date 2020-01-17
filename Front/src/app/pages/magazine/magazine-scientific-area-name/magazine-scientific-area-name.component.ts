import { Component, OnInit } from '@angular/core';
import {UserService} from '../../../service/user.service';
import {RepositoryService} from '../../../service/repository.service';
import {ActivatedRoute, Router} from '@angular/router';
import {MagazineService} from '../../../service/magazine.service';

@Component({
  selector: 'app-magazine-scientific-area-name',
  templateUrl: './magazine-scientific-area-name.component.html',
  styleUrls: ['./magazine-scientific-area-name.component.css']
})
export class MagazineScientificAreaNameComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private enumValues = [];
  private numberTemp = null;
  private greska  = null;


  constructor(private magazineService : MagazineService,
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

  onSubmit(value, form) {
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      if (value[property] == null) {
        this.greska = true;
      } else {
        o.push({fieldId: property, fieldValue: value[property]});
        this.greska = false;
      }
    }

    if (this.greska == false) {
      console.log(o);
      let x = this.magazineService.registerMagazineSAName(o, this.formFieldsDto.taskId);

      const processInstanceId = this.route.snapshot.params.processInstanceId;

      x.subscribe(
        res => {
          console.log(res);
          if (res == false) {
            alert("Uspešno ste dodali naučnu oblast");
            this.numberTemp = this.route.snapshot.params.number;
            this.numberTemp = this.numberTemp - 1;
            console.log("NUMBER TEMP JE (van petlje) : " + this.numberTemp)
            if (this.numberTemp > 0) {
              console.log("NUMBER TEMP JE: " + this.numberTemp)
              this.router.navigateByUrl('magazine-scientific-area-name/' + processInstanceId + '/' + this.numberTemp);
              this.ngOnInit();
            } else {
              this.router.navigateByUrl('/magazine-adding-reviewers-editors/' + processInstanceId);
            }
          } else {
            alert("Nije uspešno dodata naučna oblast");
            location.reload();
          }

        },
        err => {
          console.log("Error occured");
        }
      );
    } else {
      alert("Polje ne sme biti prazno! Popunite polje i probajte ponovo.");
    }
  }

}

