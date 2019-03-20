import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TutoreListComponent } from './tutore-list.component';

describe('TutoreListComponent', () => {
  let component: TutoreListComponent;
  let fixture: ComponentFixture<TutoreListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TutoreListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TutoreListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
