import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeseanceComponent } from './listeseance.component';

describe('ListeseanceComponent', () => {
  let component: ListeseanceComponent;
  let fixture: ComponentFixture<ListeseanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListeseanceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListeseanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
