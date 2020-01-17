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
import {MagazineCreationComponent} from './pages/magazine/magazine-creation/magazine-creation.component';
import {WelcomePageUserComponent} from './pages/welcome-page-user/welcome-page-user.component';
import {MagazineScientificAreaPageComponent} from './pages/magazine/magazine-scientific-area-page/magazine-scientific-area-page.component';
import {MagazineScientificAreaNameComponent} from './pages/magazine/magazine-scientific-area-name/magazine-scientific-area-name.component';
import {MagazineAddingReviewersEditorsComponent} from './pages/magazine/magazine-adding-reviewers-editors/magazine-adding-reviewers-editors.component';
import {CheckMagazineDataComponent} from './pages/magazine/check-magazine-data/check-magazine-data.component';
import {ActivationMagazineComponent} from './pages/magazine/activation-magazine/activation-magazine.component';
import {MagazineFinalPageComponent} from './pages/magazine/magazine-final-page/magazine-final-page.component';



const routes: Routes = [
  { path: '', component: WelcomePageComponent },
  { path: 'userPage', component: WelcomePageUserComponent },
  { path: 'login', component: LoginComponent},
  { path: 'login/:number/:processInstanceId', component: LoginComponent},
  { path: 'registration/:processInstanceId', component: RegistrationPageComponent},
  { path: 'activation-magazine/:processInstanceId', component: ActivationMagazineComponent },
  { path: 'magazineCreation/:processInstanceId', component: MagazineCreationComponent },
  { path: 'scientific-area/:processInstanceId', component: ScientificAreaPageComponent },
  { path: 'scientific-area-name/:processInstanceId/:number', component: ScientificAreaNameComponent },
  { path: 'activation-page/:processInstanceId/:number', component: ActivationPageComponent },
  { path: 'confirm-reviewer/:processInstanceId', component: ConfirmReviewerPageComponent} ,
  { path: 'check-magazine-data/:processInstanceId', component: CheckMagazineDataComponent} ,
  { path: 'magazine-scientific-area/:processInstanceId', component: MagazineScientificAreaPageComponent },
  { path: 'magazine-scientific-area-name/:processInstanceId/:number', component: MagazineScientificAreaNameComponent },
  { path: 'magazine-adding-reviewers-editors/:processInstanceId', component: MagazineAddingReviewersEditorsComponent },
  { path: 'magazine-final', component: MagazineFinalPageComponent },
  { path: '**', component: WelcomePageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true}), FormsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }


