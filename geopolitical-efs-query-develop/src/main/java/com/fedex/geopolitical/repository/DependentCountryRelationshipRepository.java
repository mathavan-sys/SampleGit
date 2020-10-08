package com.fedex.geopolitical.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.DependentCountryRelationship;

@Repository
public interface DependentCountryRelationshipRepository extends JpaRepository<DependentCountryRelationship, String>{

	Optional<DependentCountryRelationship> findByDependentRelationshipDescription(String dependentRelationshipDescription);
	
	@Query("select u from DependentCountryRelationship u where u.dependentRelationshipId<>?1 and u.dependentRelationshipDescription=?2")
	Optional<DependentCountryRelationship> findByDependentRelationshipDescriptionAndNotDependentRelationshipId(String dependentRelationshipId,String dependentRelationshipDescription);
	
}
