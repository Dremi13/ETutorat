import { TestBed } from '@angular/core/testing';

import { SeanceService } from './seance.service';

describe('SeanceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SeanceService = TestBed.get(SeanceService);
    expect(service).toBeTruthy();
  });
});
