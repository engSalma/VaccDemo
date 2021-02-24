package com.app.vaccnow.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.vaccnow.domain.Branch;

/**
 * Spring Data MongoDB repository for the Branch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BranchRepository extends MongoRepository<Branch, String> {
	
	List<Branch> findByVaccineStockGreaterThan(String vaccineStock);
}
