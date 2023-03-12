package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.EmployeeCompensations;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public Compensation create(Compensation compensation) {

        LOG.debug("Creating Compensation [{}]", compensation);

        compensation.setCompensationId(UUID.randomUUID().toString());
        compensationRepository.insert(compensation);

        return compensation;
    }

    @Override
    public Compensation read(String compensationId) {
        LOG.debug("Reading Compensation with id [{}]", compensationId);

        Compensation compensation = compensationRepository.findByCompensationId(compensationId);

        if (compensation == null) {
            throw new RuntimeException("Invalid CompensationId: " + compensationId);
        }

        return compensation;
    }

    @Override
    public EmployeeCompensations readEmployeeCompensations(String employeeId) {

        EmployeeCompensations employeeCompensations = new EmployeeCompensations();

        employeeCompensations.setEmployee(employeeService.read(employeeId));

        LOG.debug("Reading Compensation with id [{}]", employeeId);

        List<Compensation> compensations = compensationRepository.findByEmployeeId(employeeId);

        if (compensations == null) {
            throw new RuntimeException("No compensation found for employeeId: " + employeeId);
        }

        employeeCompensations.setCompensations(compensations);

        return employeeCompensations;
    }

    @Override
    public Compensation update(Compensation compensation) {
        LOG.debug("Updating Compensation [{}]", compensation);

        return compensationRepository.save(compensation);
    }

}
