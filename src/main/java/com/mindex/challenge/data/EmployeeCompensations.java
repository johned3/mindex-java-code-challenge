/*
 * This class is a simple DTO that provides the container for an Employee
 * and all of their Compensation records.
 *
 */

package com.mindex.challenge.data;

import java.util.List;

public class EmployeeCompensations {
    private Employee employee;
    private List<Compensation> compensations;

    public EmployeeCompensations() {
    }

    public Employee getEmployee() { return employee; }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Compensation> getCompensations() {
        return compensations;
    }

    public void setCompensations(List<Compensation> compensations) {
        this.compensations = compensations;
    }
}
