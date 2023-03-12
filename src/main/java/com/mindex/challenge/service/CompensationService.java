package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.EmployeeCompensations;

public interface CompensationService {
    Compensation create(Compensation Compensation);
    Compensation read(String id);
    EmployeeCompensations readEmployeeCompensations(String id);
    Compensation update(Compensation Compensation);
}
