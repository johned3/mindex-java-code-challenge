/*
 * This class is a simple DTO that provides the container for an Employee and their
 * number of reports, where the Employee data will include the entire object
 * graph of direct reports and their details.
 *
 */

package com.mindex.challenge.data;

public class EmployeeReportingStructure {

    private Employee employee;

    private int numberOfReports;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }
}
