import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterTutoreComponent } from './register-tutore.component';

describe('RegisterTutoreComponent', () => {
  let component: RegisterTutoreComponent;
  let fixture: ComponentFixture<RegisterTutoreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegisterTutoreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterTutoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
