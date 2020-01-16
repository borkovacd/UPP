import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { WelcomePageComponent } from './pages/welcome-page/welcome-page.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RegistrationPageComponent } from './pages/registration-page/registration-page.component';
import { UserService } from "./services/users/user.service";
import { RepositoryService } from "./services/repository/repository.service";
import { ScientificAreaPageComponent } from './pages/scientific-area-page/scientific-area-page.component';
import { ScientificAreaNameComponent } from './pages/scientific-area-name/scientific-area-name.component';
import { ActivationPageComponent } from './pages/activation-page/activation-page.component';
import { LoginComponent } from './pages/login/login.component';
import { IgxNavbarModule } from 'igniteui-angular';

@NgModule({
  declarations: [
    AppComponent,
    WelcomePageComponent,
    RegistrationPageComponent,
    ScientificAreaPageComponent,
    ScientificAreaNameComponent,
    ActivationPageComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    RouterModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    IgxNavbarModule
  ],
  providers: [
    UserService,
    RepositoryService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
