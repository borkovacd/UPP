import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivationMagazineComponent } from './activation-magazine.component';

describe('ActivationMagazineComponent', () => {
  let component: ActivationMagazineComponent;
  let fixture: ComponentFixture<ActivationMagazineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActivationMagazineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivationMagazineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
