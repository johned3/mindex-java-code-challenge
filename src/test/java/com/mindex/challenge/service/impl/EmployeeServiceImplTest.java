package com.mindex.challenge.service.impl;

import com.mindex.challenge.EmployeeTestHelper;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.EmployeeReportingStructure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static com.mindex.challenge.EmployeeTestHelper.assertEmployeeEquivalence;
import static com.mindex.challenge.EmployeeTestHelper.testEmployeeCreate;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceImplTest {
    private String employeeUrl;
    private String employeeIdUrl;
    private String employeeReportingUrl;
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
        employeeReportingUrl = "http://localhost:" + port + "/employeeReportingStructure/{id}";
    }

    @Test
    public void testCreateReadUpdate() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        // Create checks
        Employee createdEmployee = restTemplate.postForEntity(
                employeeUrl, testEmployee, Employee.class).getBody();

        assertNotNull(createdEmployee);
        assertNotNull(createdEmployee.getEmployeeId());
        EmployeeTestHelper.assertEmployeeEquivalence(testEmployee, createdEmployee);

        // Read checks
        Employee readEmployee = restTemplate.getForEntity(
                employeeIdUrl, Employee.class, createdEmployee.getEmployeeId()).getBody();
        assertNotNull(readEmployee);
        assertEquals(createdEmployee.getEmployeeId(), readEmployee.getEmployeeId());
        assertEmployeeEquivalence(createdEmployee, readEmployee);

        // Update checks
        readEmployee.setPosition("Development Manager");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Employee> entity = new HttpEntity<>(readEmployee, headers);

        headers.setContentType(MediaType.APPLICATION_JSON);

        Employee updatedEmployee =
                restTemplate.exchange(employeeIdUrl,
                        HttpMethod.PUT,
                        entity,
                        Employee.class,
                        readEmployee.getEmployeeId()).getBody();

        assertNotNull(updatedEmployee);
        assertEmployeeEquivalence(readEmployee, updatedEmployee);
    }

    @Test
    public void testReadEmployeeReportingStructure() {

        Employee testEmployee1 = testEmployeeCreate("1");
        Employee createdEmployee1 =
                restTemplate.postForEntity(employeeUrl, testEmployee1, Employee.class).getBody();
        assertNotNull(createdEmployee1);

        Employee testEmployee2 = testEmployeeCreate("2");
        Employee createdEmployee2 =
                restTemplate.postForEntity(employeeUrl, testEmployee2, Employee.class).getBody();
        assertNotNull(createdEmployee2);

        ArrayList<Employee> directReports = new ArrayList<>();
        directReports.add(createdEmployee1);
        directReports.add(createdEmployee2);

        Employee testEmployee3 = testEmployeeCreate("3");
        testEmployee3.setDirectReports(directReports);

        // Create checks
        Employee createdEmployee3 = restTemplate.postForEntity(
                employeeUrl, testEmployee3, Employee.class).getBody();

        assertNotNull(createdEmployee3);

        EmployeeReportingStructure employeeReportingStructure =
                restTemplate.getForEntity(
                        employeeReportingUrl,
                        EmployeeReportingStructure.class,
                        createdEmployee3.getEmployeeId()).getBody();

        assertNotNull(employeeReportingStructure);

        assertEquals(2, employeeReportingStructure.getNumberOfReports());

        Employee readEmployee3 = employeeReportingStructure.getEmployee();

        assertEmployeeEquivalence(readEmployee3, testEmployee3);

        for (Employee directReport: readEmployee3.getDirectReports()) {

            if(directReport.getEmployeeId().equals(createdEmployee1.getEmployeeId())) {
                assertEmployeeEquivalence(directReport, testEmployee1);
            }
            else if(directReport.getEmployeeId().equals(createdEmployee2.getEmployeeId())) {
                assertEmployeeEquivalence(directReport, testEmployee2);
            }
            else {
                fail("Direct report not found - " + directReport.getEmployeeId());
            }
        }
    }
}
