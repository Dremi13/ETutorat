import { TestBed } from '@angular/core/testing';

import { ListeseanceService } from './listeseance.service';

describe('ListeseanceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ListeseanceService = TestBed.get(ListeseanceService);
    expect(service).toBeTruthy();
  });
});

