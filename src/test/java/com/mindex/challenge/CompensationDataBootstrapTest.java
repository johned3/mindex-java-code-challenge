package com.mindex.challenge;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompensationDataBootstrapTest {
    @Autowired
    private CompensationRepository compensationRepository;

    @Test
    public void testCompensation() {
        Compensation compensation = compensationRepository.findByCompensationId("bcd39309-3348-463b-a7e3-5de1c168beb3");
        assertNotNull(compensation);
        assertEquals("b7839309-3348-463b-a7e3-5de1c168beb3", compensation.getEmployeeId());
        assertEquals(BigDecimal.valueOf(700000000), compensation.getSalary());
        assertEquals(LocalDate.of(1966,8,5), compensation.getEffectiveDate());
    }
}