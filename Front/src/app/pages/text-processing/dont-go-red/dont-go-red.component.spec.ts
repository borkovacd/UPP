import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DontGoRedComponent } from './dont-go-red.component';

describe('DontGoRedComponent', () => {
  let component: DontGoRedComponent;
  let fixture: ComponentFixture<DontGoRedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DontGoRedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DontGoRedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
