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
import { MagazineService } from "./services/magazines/magazine.service";
import { RepositoryService } from "./services/repository/repository.service";
import { ScientificAreaPageComponent } from './pages/scientific-area-page/scientific-area-page.component';
import { ScientificAreaNameComponent } from './pages/scientific-area-name/scientific-area-name.component';
import { ActivationPageComponent } from './pages/activation-page/activation-page.component';
import { LoginComponent } from './pages/login/login.component';
import { ConfirmReviewerPageComponent } from './pages/confirm-reviewer-page/confirm-reviewer-page.component';
import { WelcomePageUserComponent } from './pages/welcome-page-user/welcome-page-user.component';
import { MagazineCreationComponent } from './pages/magazine-creation/magazine-creation.component';

@NgModule({
  declarations: [
    AppComponent,
    WelcomePageComponent,
    RegistrationPageComponent,
    ScientificAreaPageComponent,
    ScientificAreaNameComponent,
    ActivationPageComponent,
    LoginComponent,
    ConfirmReviewerPageComponent,
    WelcomePageUserComponent,
    MagazineCreationComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    RouterModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
  ],
  providers: [
    UserService,
    RepositoryService,
    MagazineService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
