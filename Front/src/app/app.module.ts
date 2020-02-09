import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { WelcomePageComponent } from './pages/welcome-page/welcome-page.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RegistrationPageComponent } from './pages/registration/registration-page/registration-page.component';
import { UserService} from './service/user.service';
import { MagazineService} from './service/magazine.service';
import { RepositoryService} from './service/repository.service';
import { ScientificAreaPageComponent } from './pages/registration/scientific-area-page/scientific-area-page.component';
import { ScientificAreaNameComponent } from './pages/registration/scientific-area-name/scientific-area-name.component';
import { ActivationPageComponent } from './pages/registration/activation-page/activation-page.component';
import { LoginComponent } from './pages/login/login.component';
import { ConfirmReviewerPageComponent } from './pages/registration/confirm-reviewer-page/confirm-reviewer-page.component';
import { WelcomePageUserComponent } from './pages/welcome-page-user/welcome-page-user.component';
import { MagazineCreationComponent } from './pages/magazine/magazine-creation/magazine-creation.component';
import { MagazineScientificAreaPageComponent } from './pages/magazine/magazine-scientific-area-page/magazine-scientific-area-page.component';
import { MagazineScientificAreaNameComponent } from './pages/magazine/magazine-scientific-area-name/magazine-scientific-area-name.component';
import { MagazineAddingReviewersEditorsComponent } from './pages/magazine/magazine-adding-reviewers-editors/magazine-adding-reviewers-editors.component';
import { CheckMagazineDataComponent } from './pages/magazine/check-magazine-data/check-magazine-data.component';
import { ActivationMagazineComponent } from './pages/magazine/activation-magazine/activation-magazine.component';
import { MagazineFinalPageComponent } from './pages/magazine/magazine-final-page/magazine-final-page.component';
import {AuthInterceptor} from './http-interceptor/AuthInterceptor';
import {AuthService} from './service/auth.service';
import { NeedRegistrationComponent } from './pages/text-processing/need-registration/need-registration.component';
import { ConfirmLoggingInComponent } from './pages/text-processing/confirm-logging-in/confirm-logging-in.component';
import { ChoosingMagazineComponent } from './pages/text-processing/choosing-magazine/choosing-magazine.component';
import { AuthorLoginComponent } from './pages/text-processing/author-login/author-login.component';
import { TextInformationComponent } from './pages/text-processing/text-information/text-information.component';
import { CoauthorDataComponent } from './pages/text-processing/coauthor-data/coauthor-data.component';
import { UserInformingComponent } from './pages/text-processing/user-informing/user-informing.component';
import { ArticleReviewComponent } from './pages/text-processing/article-review/article-review.component';
import { EndPageComponent } from './pages/text-processing/end-page/end-page.component';
import { PdfReviewComponent } from './pages/text-processing/pdf-review/pdf-review.component';
import { ChiefEditorInformingComponent } from './pages/text-processing/chief-editor-informing/chief-editor-informing.component';
import { ArticleCorrectionComponent } from './pages/text-processing/article-correction/article-correction.component';
import { ChoosingReviewersComponent } from './pages/text-processing/choosing-reviewers/choosing-reviewers.component';
import { DefineReviewTimeComponent } from './define-review-time/define-review-time.component';
import { ChiefEditorNoCorrectionComponent } from './pages/text-processing/chief-editor-no-correction/chief-editor-no-correction.component';

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
    MagazineCreationComponent,
    MagazineScientificAreaPageComponent,
    MagazineScientificAreaNameComponent,
    MagazineAddingReviewersEditorsComponent,
    CheckMagazineDataComponent,
    ActivationMagazineComponent,
    MagazineFinalPageComponent,
    NeedRegistrationComponent,
    ConfirmLoggingInComponent,
    ChoosingMagazineComponent,
    AuthorLoginComponent,
    TextInformationComponent,
    CoauthorDataComponent,
    UserInformingComponent,
    ArticleReviewComponent,
    EndPageComponent,
    PdfReviewComponent,
    ChiefEditorInformingComponent,
    ArticleCorrectionComponent,
    ChoosingReviewersComponent,
    DefineReviewTimeComponent,
    ChiefEditorNoCorrectionComponent
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
    MagazineService,
    AuthService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
