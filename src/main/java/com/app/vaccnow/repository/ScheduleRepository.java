package com.app.vaccnow.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.vaccnow.domain.Branch;
import com.app.vaccnow.domain.Schedule;

/**
 * Spring Data MongoDB repository for the Schedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {
	
	List<Schedule> findByBranch(Branch branch);
	
	List<Schedule> findByDate(LocalDate date);
	
	Schedule findByDateAndBranch(LocalDate date, Branch branch);
	
}
