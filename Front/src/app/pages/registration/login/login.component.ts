import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators, FormBuilder, NgForm, AbstractControl} from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { UserService} from '../../../service/user.service';
import {LoginModel} from '../../../model/login.model';
import {UserModel} from '../../../model/user.model';
import {AuthService} from '../../../service/auth.service';
import {KorisnikModel} from '../../../model/Korisnik.model';
import {HttpClient} from '@angular/common/http';

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
  errorMessage : String;

  user : KorisnikModel = new KorisnikModel();

  constructor(private route: ActivatedRoute,
              private fb: FormBuilder,
              private userService: UserService,
              private authService: AuthService,
              private http: HttpClient) {
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

    this.user.username = this.username.value;
    this.user.password = this.password.value;

    this.authService.login(this.user).subscribe(
      success => {

        if (!success) {
          alert('Neispravno korisnicko ime ili lozinka!');
          this.errorMessage = "Wrong email or password";
        } else {
          this.authService.getCurrentUser().subscribe(
            data => {
              //localStorage.setItem("ROLE", data.uloga);
              localStorage.setItem("USERNAME", data.username);
              window.location.href = '/userPage';

            }
          )
        }
      }
    );

    /*this.processInstanceId = this.route.snapshot.params.processInstanceId;
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
    }*/
  }

}
