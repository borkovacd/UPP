import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DefineCorrectionTimeComponent } from './define-correction-time.component';

describe('DefineCorrectionTimeComponent', () => {
  let component: DefineCorrectionTimeComponent;
  let fixture: ComponentFixture<DefineCorrectionTimeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DefineCorrectionTimeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DefineCorrectionTimeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
