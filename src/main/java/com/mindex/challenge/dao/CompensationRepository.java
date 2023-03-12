/*
 * This class is an extension of a MongoRespository with two find methods
 * that magically work without implementation thanks to Spring and Mongo.
 *
 */

package com.mindex.challenge.dao;

import com.mindex.challenge.data.Compensation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String> {
    Compensation findByCompensationId(String compensationId);
    List<Compensation> findByEmployeeId(String employeeId);
}
