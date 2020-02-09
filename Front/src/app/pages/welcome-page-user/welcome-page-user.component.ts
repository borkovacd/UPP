import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {RepositoryService} from '../../service/repository.service';
import {UserService} from '../../service/user.service';
import {TextProcessingService} from '../../service/text-processing.service';

@Component({
  selector: 'app-welcome-page-user',
  templateUrl: './welcome-page-user.component.html',
  styleUrls: ['./welcome-page-user.component.css']
})
export class WelcomePageUserComponent implements OnInit {

  private processInstance = "";
  private loggedUser = null;
  private username = null;
  private userType = null;
  private enumValues = [];
  private tasks = null;
  private claimedTaskId = null;
  private formFieldsDto = null;
  private formFields = [];
  private notClaimed: boolean;

  constructor(public router: Router,
              private repositoryService : RepositoryService,
              private textProcessingService: TextProcessingService,
              private userService: UserService) {

  }

  ngOnInit() {

    this.loggedUser = localStorage.getItem("USERNAME");
    this.username = localStorage.getItem("USERNAME");
    this.userType = localStorage.getItem('ROLE');

    this.notClaimed = true;
    this.tasks = null;

    let x = this.userService.getTasksOfUser(this.username);

    x.subscribe(
      res => {
        console.log(res);
        this.tasks = res;
      },
      err => {
        console.log('Error occured');
      }
    );

  }


  createMagazine() {

    this.repositoryService.startProcessCreateMagazine(this.username).subscribe(data => {
      console.log(data);
      this.processInstance = data.processInstanceId;
      this.router.navigateByUrl('/magazineCreation/' + this.processInstance);
    })

  }

  logOut() {
    localStorage.clear();
    alert('Successfully logged out!');
    window.location.href = '';
  }

  submitNewText() {
    this.textProcessingService.startTextProcessingProcess().subscribe(data => {
      console.log(data);
      this.processInstance = data.processInstanceId;

      this.userService.userIsLoggedIn().subscribe( data => {
        console.log("There is a logged in user " + data);
        if(data == false) {
          this.router.navigateByUrl('/need-registration/' + this.processInstance);
        } else {
          this.router.navigateByUrl('/choosing-magazine/' + this.processInstance);
        }
      })
    })
  }

  /*getTasks(){
    //const processInstanceId = this.route.snapshot.params.processInstanceId;
    let x = this.repositoryService.getTasks(this.processInstance);

    x.subscribe(
      res => {
        console.log(res);
        this.tasks = res;
      },
      err => {
        console.log("Error occured");
      }
    );
  }*/

  /*claim(taskId){
    let x = this.repositoryService.claimTask(taskId);

    x.subscribe(
      res => {
        console.log(res);


        this.claimedTaskId = taskId;

        let y = this.repositoryService.getNewTask(taskId);

        y.subscribe(
          res => {
            console.log(res);
            this.formFieldsDto = res;
            this.formFields = res.formFields;
            //this.processInstance = res.processInstanceId;
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

      },
      err => {
        console.log("Error occured");
      }
    );
  }

  complete(taskId){
    //const processInstanceId = this.route.snapshot.params.processInstanceId;
    let x = this.repositoryService.completeTask(this.processInstance,taskId);

    x.subscribe(
      res => {
        console.log(res);
        this.tasks = res;
      },
      err => {
        console.log("Error occured");
      }
    );
  }*/

  start(taskId){
    let x = this.userService.startTask(taskId);
    console.log('Usao u izvrši task');
  }
}
