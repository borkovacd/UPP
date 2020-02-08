import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmLoggingInComponent } from './confirm-logging-in.component';

describe('ConfirmLoggingInComponent', () => {
  let component: ConfirmLoggingInComponent;
  let fixture: ComponentFixture<ConfirmLoggingInComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfirmLoggingInComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmLoggingInComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
