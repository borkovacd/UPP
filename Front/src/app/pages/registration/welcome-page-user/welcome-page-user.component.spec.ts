import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WelcomePageUserComponent } from './welcome-page-user.component';

describe('WelcomePageUserComponent', () => {
  let component: WelcomePageUserComponent;
  let fixture: ComponentFixture<WelcomePageUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WelcomePageUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WelcomePageUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
