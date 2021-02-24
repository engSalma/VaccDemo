package com.app.vaccnow.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.vaccnow.domain.Timeslot;

/**
 * Spring Data MongoDB repository for the Timeslot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeslotRepository extends MongoRepository<Timeslot, String> {
	
	List<Timeslot> findByStatus(String status);
	
	List<Timeslot> findByFromAfterAndStatus(ZonedDateTime from, String status);
	
	List<Timeslot> findByFromAfterAndToBeforeAndStatus(ZonedDateTime from, ZonedDateTime to, String status);
}
