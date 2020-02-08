import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserInformingComponent } from './user-informing.component';

describe('UserInformingComponent', () => {
  let component: UserInformingComponent;
  let fixture: ComponentFixture<UserInformingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserInformingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserInformingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
