import { Component, OnInit } from '@angular/core';
import {UserService} from '../../../service/user.service';
import {RepositoryService} from '../../../service/repository.service';
import {TextProcessingService} from '../../../service/text-processing.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-dont-go-red',
  templateUrl: './dont-go-red.component.html',
  styleUrls: ['./dont-go-red.component.css']
})
export class DontGoRedComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private enumValues = [];

  private loggedUser = null;




  constructor(private userService : UserService,
              private repositoryService : RepositoryService,
              private textProcessingService: TextProcessingService,
              public router: Router,
              private route: ActivatedRoute) {

  }

  ngOnInit() {

    this.loggedUser = localStorage.getItem("USERNAME");

    const processInstanceId = this.route.snapshot.params.processInstanceId;

    let x = this.textProcessingService.getTaskForm(processInstanceId);

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

    const processInstanceId = this.route.snapshot.params.processInstanceId;

    console.log(o);
    let x = this.textProcessingService.confirmLoggingIn(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);
        this.router.navigateByUrl('/choosing-magazine/' + processInstanceId);
      },
      err => {
        console.log("Error occured");
      }
    );
  }

  logOut() {
    /*let x =  this.userService.logoutUser();
    x.subscribe(
      res => {
        alert('Successfully logged out!');
        sessionStorage.clear();
        //sessionStorage.setItem('loggedUser', null);
        window.location.href = '';
      },
      err => {
        console.log('Mistake!');
      }
    );*/

    localStorage.clear();
    alert('Successfully logged out!');
    window.location.href = '';
  }

}

