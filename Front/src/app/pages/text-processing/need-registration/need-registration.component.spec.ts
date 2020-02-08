import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NeedRegistrationComponent } from './need-registration.component';

describe('NeedRegistrationComponent', () => {
  let component: NeedRegistrationComponent;
  let fixture: ComponentFixture<NeedRegistrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NeedRegistrationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NeedRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
