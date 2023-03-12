package com.mindex.challenge.service;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.EmployeeReportingStructure;

public interface EmployeeService {
    Employee create(Employee employee);
    Employee read(String id);
    EmployeeReportingStructure readReportingStructure(String id);
    Employee update(Employee employee);

}
