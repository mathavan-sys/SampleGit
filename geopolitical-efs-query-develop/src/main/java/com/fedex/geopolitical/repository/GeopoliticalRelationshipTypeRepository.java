package com.fedex.geopolitical.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.GeopoliticalRelationshipType;

@Repository
public interface GeopoliticalRelationshipTypeRepository extends JpaRepository<GeopoliticalRelationshipType, String> {

	Optional<GeopoliticalRelationshipType> findByAreaRelationshipTypeDescription(
			String areaRelationshipTypeDescription);

	Optional<GeopoliticalRelationshipType> findByGeopoliticalRelationshipTypeCd(String geopoliticalRelationshipTypeCd);

	@Query("select u from GeopoliticalRelationshipType u where u.geopoliticalRelationshipTypeCd<>?1 and u.areaRelationshipTypeDescription=?2")
	Optional<GeopoliticalRelationshipType> findByAreaRelationshipTypeDescriptionAndNotGeopoliticalRelationshipTypeCd(
			String geopoliticalRelationshipTypeCd, String areaRelationshipTypeDescription);

}
