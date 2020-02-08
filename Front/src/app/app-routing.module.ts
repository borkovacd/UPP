import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {WelcomePageComponent} from './pages/welcome-page/welcome-page.component';
import {FormsModule} from '@angular/forms';
import {RegistrationPageComponent} from './pages/registration/registration-page/registration-page.component';
import {ScientificAreaPageComponent} from "./pages/registration/scientific-area-page/scientific-area-page.component";
import {ScientificAreaNameComponent} from "./pages/registration/scientific-area-name/scientific-area-name.component";
import {ActivationPageComponent} from "./pages/registration/activation-page/activation-page.component";
import {LoginComponent} from './pages/login/login.component';
import {ConfirmReviewerPageComponent} from './pages/registration/confirm-reviewer-page/confirm-reviewer-page.component';
import {MagazineCreationComponent} from './pages/magazine/magazine-creation/magazine-creation.component';
import {WelcomePageUserComponent} from './pages/welcome-page-user/welcome-page-user.component';
import {MagazineScientificAreaPageComponent} from './pages/magazine/magazine-scientific-area-page/magazine-scientific-area-page.component';
import {MagazineScientificAreaNameComponent} from './pages/magazine/magazine-scientific-area-name/magazine-scientific-area-name.component';
import {MagazineAddingReviewersEditorsComponent} from './pages/magazine/magazine-adding-reviewers-editors/magazine-adding-reviewers-editors.component';
import {CheckMagazineDataComponent} from './pages/magazine/check-magazine-data/check-magazine-data.component';
import {ActivationMagazineComponent} from './pages/magazine/activation-magazine/activation-magazine.component';
import {MagazineFinalPageComponent} from './pages/magazine/magazine-final-page/magazine-final-page.component';
import {NeedRegistrationComponent} from './pages/text-processing/need-registration/need-registration.component';
import {ChoosingMagazineComponent} from './pages/text-processing/choosing-magazine/choosing-magazine.component';
import {ConfirmLoggingInComponent} from './pages/text-processing/confirm-logging-in/confirm-logging-in.component';
import {AuthorLoginComponent} from './pages/text-processing/author-login/author-login.component';
import {TextInformationComponent} from './pages/text-processing/text-information/text-information.component';
import {CoauthorDataComponent} from './pages/text-processing/coauthor-data/coauthor-data.component';
import {UserInformingComponent} from './pages/text-processing/user-informing/user-informing.component';



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
  { path: 'need-registration/:processInstanceId', component: NeedRegistrationComponent },
  { path: 'choosing-magazine/:processInstanceId', component: ChoosingMagazineComponent },
  { path: 'confirm-logging-in/:processInstanceId', component: ConfirmLoggingInComponent },
  { path: 'login/:processInstanceId', component: AuthorLoginComponent},
  { path: 'text-information/:processInstanceId', component: TextInformationComponent },
  { path: 'coauthor-data/:processInstanceId/:number', component:  CoauthorDataComponent},
  { path: 'user-informing', component:  UserInformingComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true}), FormsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }


