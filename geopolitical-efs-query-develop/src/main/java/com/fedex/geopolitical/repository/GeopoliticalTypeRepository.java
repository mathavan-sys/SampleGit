package com.fedex.geopolitical.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.GeopoliticalType;


@Repository
public interface GeopoliticalTypeRepository extends JpaRepository<GeopoliticalType,Long> {
	
	Optional<GeopoliticalType> findByGeopoliticalTypeName(String geopoliticalTypeName);
	
	@Query("select u from GeopoliticalType u where u.geopoliticalTypeId<>?1 and u.geopoliticalTypeName=?2")
	Optional<GeopoliticalType> findByGeopoliticalTypeNameAndNotGeopoliticalTypeId(long geopoliticalTypeId,String geopoliticalTypeName);

}
