/**
 * Helper for Employee related code used by multiple dependent test classes.
 */

package com.mindex.challenge;

import com.mindex.challenge.data.Employee;
import static org.junit.Assert.*;

public class EmployeeTestHelper {
    public static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

    public static Employee testEmployeeCreate(String suffix) {
        Employee employee = new Employee();
        employee.setFirstName("First" + suffix);
        employee.setLastName("Last" + suffix);
        employee.setDepartment("Dept" + suffix);
        employee.setPosition("Pos" + suffix);
        return employee;
    }

}
