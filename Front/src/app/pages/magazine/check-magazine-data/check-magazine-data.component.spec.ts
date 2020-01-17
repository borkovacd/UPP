import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckMagazineDataComponent } from './check-magazine-data.component';

describe('CheckMagazineDataComponent', () => {
  let component: CheckMagazineDataComponent;
  let fixture: ComponentFixture<CheckMagazineDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CheckMagazineDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckMagazineDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
