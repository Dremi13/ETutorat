import { TestBed } from '@angular/core/testing';

import { AdminSeanceService } from './admin-seance.service';

describe('AdminSeanceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AdminSeanceService = TestBed.get(AdminSeanceService);
    expect(service).toBeTruthy();
  });
});
