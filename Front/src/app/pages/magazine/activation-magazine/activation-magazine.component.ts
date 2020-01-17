import { Component, OnInit } from '@angular/core';
import {UserService} from '../../../service/user.service';
import {RepositoryService} from '../../../service/repository.service';
import {ActivatedRoute, Router} from '@angular/router';
import {MagazineService} from '../../../service/magazine.service';

@Component({
  selector: 'app-activation-magazine',
  templateUrl: './activation-magazine.component.html',
  styleUrls: ['./activation-magazine.component.css']
})
export class ActivationMagazineComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private enumValues = [];
  private numberTemp = null;
  private greska  = null;
  private ispravljanje = null;


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
        this.ispravljanje = value[property];
      }
    }

    if (this.greska == false) {
      console.log(o);
      let x = this.magazineService.setActivationForMagazine(o, this.formFieldsDto.taskId);

      const processInstanceId = this.route.snapshot.params.processInstanceId;

      x.subscribe(
        res => {
          console.log(res);
          alert("Odlucili ste o aktivaciji casopisa");
          this.router.navigateByUrl('/magazine-final');
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


