package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.EmployeeCompensations;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
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

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.mindex.challenge.EmployeeTestHelper.assertEmployeeEquivalence;
import static com.mindex.challenge.EmployeeTestHelper.testEmployeeCreate;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationUrl;
    private String compensationIdUrl;
    private String compensationsByEmpIdUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    CompensationService compensationService;
    @Autowired
    private EmployeeService employeeService;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
        compensationsByEmpIdUrl = "http://localhost:" + port + "/compensationsByEmployee/{id}";
    }

    @Test
    public void testCreateReadUpdate() {

        Employee testEmployee = employeeService.create(testEmployeeCreate("1"));

        Compensation testCompensation = new Compensation();
        testCompensation.setEmployeeId(testEmployee.getEmployeeId());
        testCompensation.setSalary(BigDecimal.valueOf(1000.50));
        testCompensation.setEffectiveDate(LocalDate.now());

        // Create checks
        Compensation createdCompensation =
                restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();

        assertNotNull(createdCompensation);
        assertNotNull(createdCompensation.getCompensationId());
        assertCompensationEquivalence(testCompensation, createdCompensation);

        // Read checks
        Compensation readCompensation =
                restTemplate.getForEntity(compensationIdUrl, Compensation.class,
                        createdCompensation.getCompensationId()).getBody();

        assertNotNull(readCompensation);
        assertEquals(createdCompensation.getCompensationId(), readCompensation.getCompensationId());
        assertCompensationEquivalence(createdCompensation, readCompensation);

        // Update checks
        readCompensation.setSalary(BigDecimal.valueOf(50000));

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Compensation> entity = new HttpEntity<>(readCompensation, headers);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Compensation updatedCompensation =
                restTemplate.exchange(compensationIdUrl,
                        HttpMethod.PUT,
                        entity,
                        Compensation.class,
                        readCompensation.getCompensationId()).getBody();

        assertNotNull(updatedCompensation);
        assertCompensationEquivalence(readCompensation, updatedCompensation);
    }

    @Test
    public void testReadEmployeeCompensations() {

        Employee testEmployee = testEmployeeCreate("2");
        Employee createdEmployee = employeeService.create(testEmployee);

        Compensation testCompensation1 = new Compensation();
        testCompensation1.setEmployeeId(createdEmployee.getEmployeeId());
        testCompensation1.setSalary(BigDecimal.valueOf(1010101.01));
        testCompensation1.setEffectiveDate(LocalDate.of(1969,9,26));
        Compensation createdCompensation1=
                restTemplate.postForEntity(compensationUrl, testCompensation1, Compensation.class).getBody();
        assertNotNull(createdCompensation1);

        Compensation testCompensation2 = new Compensation();
        testCompensation2.setEmployeeId(createdEmployee.getEmployeeId());
        testCompensation2.setSalary(BigDecimal.valueOf(2020202.02));
        testCompensation2.setEffectiveDate(LocalDate.of(1966,8,5));
        Compensation createdCompensation2 =
                restTemplate.postForEntity(compensationUrl, testCompensation2, Compensation.class).getBody();
        assertNotNull(createdCompensation2);

        EmployeeCompensations readEmployeeCompensations = restTemplate.getForEntity(
                compensationsByEmpIdUrl, EmployeeCompensations.class, createdEmployee.getEmployeeId()).getBody();
        assertNotNull(readEmployeeCompensations);
        assertEmployeeEquivalence(readEmployeeCompensations.getEmployee(), testEmployee);

        for (Compensation compensation: readEmployeeCompensations.getCompensations()) {

            if(compensation.getCompensationId().equals(createdCompensation1.getCompensationId())) {
                assertCompensationEquivalence(compensation, testCompensation1);
            }
            else if(compensation.getCompensationId().equals(createdCompensation2.getCompensationId())) {
                assertCompensationEquivalence(compensation, testCompensation2);
            }
            else {
                fail("Direct report not found - " + compensation.getCompensationId());
            }
        }
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }

}
