import { TestBed, async, inject } from '@angular/core/testing';

import { AdminNotsigninGuard } from './admin-notsignin.guard';

describe('AdminNotsigninGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AdminNotsigninGuard]
    });
  });

  it('should ...', inject([AdminNotsigninGuard], (guard: AdminNotsigninGuard) => {
    expect(guard).toBeTruthy();
  }));
});
