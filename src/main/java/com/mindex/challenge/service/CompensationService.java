/*
 * This is interface for the Compensation entity.  Compensation  is a record of
 * the association between an Employee and their Salary. Employee may have multiple
 * Compensation records.  I could have gone with a single record per Employee but
 * multiple is more typical, and it seemed wrong to be pulling up a single record where
 * there is no guarantee that there is only one.
 *
 */

package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.EmployeeCompensations;

public interface CompensationService {
    Compensation create(Compensation Compensation);
    Compensation read(String id);
    EmployeeCompensations readEmployeeCompensations(String id);
    Compensation update(Compensation Compensation);
}
