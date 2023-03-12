/*
 * This class is a simple DTO that provides the container for the Compensation entity.
 * More details are in CompensationService.
 *
 */

package com.mindex.challenge.data;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Compensation {
    private String compensationId;
    private String employeeId;
    private BigDecimal salary;
    private LocalDate effectiveDate;

    public String getCompensationId() {
        return compensationId;
    }

    public void setCompensationId(String compensationId) {
        this.compensationId = compensationId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
