import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChoosingMagazineComponent } from './choosing-magazine.component';

describe('ChoosingMagazineComponent', () => {
  let component: ChoosingMagazineComponent;
  let fixture: ComponentFixture<ChoosingMagazineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChoosingMagazineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChoosingMagazineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
