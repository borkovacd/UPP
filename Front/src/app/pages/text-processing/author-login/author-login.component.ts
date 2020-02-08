import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {KorisnikModel} from '../../../model/Korisnik.model';
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../../../service/user.service';
import {AuthService} from '../../../service/auth.service';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-author-login',
  templateUrl: './author-login.component.html',
  styleUrls: ['./author-login.component.css']
})
export class AuthorLoginComponent implements OnInit {

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

    this.processInstanceId = this.route.snapshot.params.processInstanceId;

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
              window.location.href = '/confirm-logging-in/' + this.processInstanceId;

            }
          )
        }
      }
    );
  }

}
