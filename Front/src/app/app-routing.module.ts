import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {WelcomePageComponent} from './pages/welcome-page/welcome-page.component';
import {FormsModule} from '@angular/forms';
import {RegistrationPageComponent} from './pages/registration-page/registration-page.component';
import {ScientificAreaPageComponent} from "./pages/scientific-area-page/scientific-area-page.component";
import {ScientificAreaNameComponent} from "./pages/scientific-area-name/scientific-area-name.component";
import {ActivationPageComponent} from "./pages/activation-page/activation-page.component";
import {LoginComponent} from './pages/login/login.component';
import {ConfirmReviewerPageComponent} from './pages/confirm-reviewer-page/confirm-reviewer-page.component';



const routes: Routes = [
  { path: '', component: WelcomePageComponent },
  { path: 'login', component: LoginComponent},
  { path: 'login/:processInstanceId', component: LoginComponent},
  { path: 'registration/:processInstanceId', component: RegistrationPageComponent},
  { path: 'scientific-area/:processInstanceId', component: ScientificAreaPageComponent },
  { path: 'scientific-area-name/:processInstanceId/:number', component: ScientificAreaNameComponent },
  { path: 'activation-page/:processInstanceId/:number', component: ActivationPageComponent },
  { path: 'confirm-reviewer/:processInstanceId', component: ConfirmReviewerPageComponent} ,
  { path: '**', component: WelcomePageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true}), FormsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }


