import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TextInformationComponent } from './text-information.component';

describe('TextInformationComponent', () => {
  let component: TextInformationComponent;
  let fixture: ComponentFixture<TextInformationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TextInformationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TextInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
