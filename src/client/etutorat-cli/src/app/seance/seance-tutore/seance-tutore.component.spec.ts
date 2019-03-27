import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SeanceTutoreComponent } from './seance-tutore.component';

describe('SeanceTutoreComponent', () => {
  let component: SeanceTutoreComponent;
  let fixture: ComponentFixture<SeanceTutoreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeanceTutoreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeanceTutoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
