import { Component, OnInit } from '@angular/core';
import {RepositoryService} from '../../services/repository/repository.service';
import {MagazineService} from '../../services/magazines/magazine.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-magazine-creation',
  templateUrl: './magazine-creation.component.html',
  styleUrls: ['./magazine-creation.component.css']
})
export class MagazineCreationComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processInstance = "";
  private enumValues = [];
  private tasks = [];
  private claimedTaskId = null;

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

  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);
    let x = this.magazineService.registerMagazine(o, this.formFieldsDto.taskId);

    const processInstanceId = this.route.snapshot.params.processInstanceId;

    x.subscribe(
      res => {
        console.log(res);
        if(res==false) {
          alert("Registracija novog časopisa uspešno izvršena!");
          this.router.navigateByUrl('scientific-area/' + processInstanceId);
        } else {
          alert("Nije uspešno izvršena registracija novog časopisa");
          location.reload();
        }
      },
      err => {
        console.log("Error occured");
      }
    );
  }

}
