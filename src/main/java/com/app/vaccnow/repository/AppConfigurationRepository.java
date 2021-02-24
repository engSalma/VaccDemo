package com.app.vaccnow.repository;

import com.app.vaccnow.domain.AppConfiguration;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the AppConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppConfigurationRepository extends MongoRepository<AppConfiguration, String> {
	
	AppConfiguration findByKey(String key);
}
