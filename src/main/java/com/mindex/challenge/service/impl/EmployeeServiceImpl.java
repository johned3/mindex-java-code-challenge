package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.EmployeeReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Fetching employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }


    @Override
    public EmployeeReportingStructure readReportingStructure(String id) {

        LOG.debug("Fetching employee reporting structure with id [{}]", id);

        EmployeeReportingStructure employeeReportingStructure = new EmployeeReportingStructure();
        Employee employee = read(id);
        int numberOfReports = 0;

        List<Employee> directReports = employee.getDirectReports();

        if (directReports != null && !directReports.isEmpty()) {

            numberOfReports = directReports.size();

            List<Employee> directReportsWithTree = new ArrayList<>();

            for (Employee directReport : directReports) {

                EmployeeReportingStructure directReportStructure =
                        readReportingStructure(directReport.getEmployeeId());

                directReportsWithTree.add(directReportStructure.getEmployee());

                numberOfReports += directReportStructure.getNumberOfReports();
            }

            employee.setDirectReports(directReportsWithTree);
        }

        employeeReportingStructure.setEmployee(employee);
        employeeReportingStructure.setNumberOfReports(numberOfReports);

        return employeeReportingStructure;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

}
