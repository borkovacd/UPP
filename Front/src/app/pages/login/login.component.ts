import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators, FormBuilder, NgForm, AbstractControl} from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { UserService} from '../../service/user.service';
import {LoginModel} from '../../model/login.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public form: FormGroup;
  public username: AbstractControl;
  public password: AbstractControl;
  private processInstanceId = null;
  private number = null;

  constructor(private route: ActivatedRoute,
              private fb: FormBuilder,
              private userService: UserService) {
    this.form = this.fb.group({
      'username' : ['', Validators.compose([Validators.required])],
      'password' : ['', Validators.compose([Validators.required])],
    })
    this.username = this.form.controls['username'];
    this.password = this.form.controls['password'];
  }

  ngOnInit() {
  }

  logIn() {

    const login = new LoginModel(
      this.username.value,
      this.password.value,
    )

    this.processInstanceId = this.route.snapshot.params.processInstanceId;
    this.number = this.route.snapshot.params.number;
    if(this.processInstanceId==null) {
      let y = this.userService.loginUser(login);
      console.log('Pre subscribe');
      y.subscribe(
      res => {
          console.log(res);
          alert('Successfully logged in!');
          sessionStorage.setItem('loggedUser', JSON.stringify(res));
          window.location.href = '/userPage';
        },
        err => {
          console.log('Username or password are not correct!');
        }
      );
    } else {
      let y = this.userService.loginAdmin(login);
      console.log('Pre subscribe');
      y.subscribe(
        res => {
          console.log(res);
          alert('Successfully logged in!');
          sessionStorage.setItem('loggedUser', JSON.stringify(res));
          if(this.number == 0) {
            window.location.href = '/confirm-reviewer/' + this.processInstanceId;
          } else if(this.number == 1) {
            window.location.href = '/check-magazine-data/' + this.processInstanceId;
          }
        },
        err => {
          console.log('Mora se ulogovati admin');
        }
      );
    }
  }

}
