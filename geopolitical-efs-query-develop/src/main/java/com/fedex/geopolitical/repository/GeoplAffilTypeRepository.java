package com.fedex.geopolitical.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.GeoplAffilType;


@Repository
public interface GeoplAffilTypeRepository extends JpaRepository<GeoplAffilType,Long> {
	
	Optional<GeoplAffilType> findByAffilTypeCode(String affilTypeCode);
	
	Optional<GeoplAffilType> findByAffilTypeName(String affilTypeName);

	@Query("select g from GeoplAffilType g where g.affilTypeId<>?1 and g.affilTypeCode=?2")
	Optional<GeoplAffilType> findByAffilIdAndNotAffilCode(long affilTypeId, String affilTypeCode);
}
