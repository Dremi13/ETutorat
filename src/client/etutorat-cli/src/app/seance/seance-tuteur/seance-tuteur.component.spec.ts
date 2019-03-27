import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SeanceTuteurComponent } from './seance-tuteur.component';

describe('SeanceTuteurComponent', () => {
  let component: SeanceTuteurComponent;
  let fixture: ComponentFixture<SeanceTuteurComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeanceTuteurComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeanceTuteurComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
