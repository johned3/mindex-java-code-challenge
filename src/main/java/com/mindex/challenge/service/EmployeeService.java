package com.mindex.challenge.service;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.EmployeeReportingStructure;

public interface EmployeeService {
    Employee create(Employee employee);
    Employee read(String id);

    /**
     * Since there is no new data this is associated with, just more detail,
     * and no use case for creating this as a separate service, I put it here
     * and just retrieving more details in the object graph.
     */
    EmployeeReportingStructure readReportingStructure(String id);
    Employee update(Employee employee);

}
