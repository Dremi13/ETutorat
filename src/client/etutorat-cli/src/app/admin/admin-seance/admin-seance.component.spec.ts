import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminSeanceComponent } from './admin-seance.component';

describe('AdminSeanceComponent', () => {
  let component: AdminSeanceComponent;
  let fixture: ComponentFixture<AdminSeanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminSeanceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminSeanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
