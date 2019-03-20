import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTutoreComponent } from './create-tutore.component';

describe('CreateTutoreComponent', () => {
  let component: CreateTutoreComponent;
  let fixture: ComponentFixture<CreateTutoreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateTutoreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateTutoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
