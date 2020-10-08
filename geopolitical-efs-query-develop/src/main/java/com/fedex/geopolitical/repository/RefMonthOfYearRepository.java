package com.fedex.geopolitical.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.RefMonthOfYear;

@Repository
public interface RefMonthOfYearRepository extends JpaRepository<RefMonthOfYear, Long>{

	Optional<RefMonthOfYear> findByMonthOfYearShortName(String monthOfYearShortName);
	
	Optional<RefMonthOfYear> findByMonthOfYearNumber(long monthOfYearNumber);
	
	@Query("select r from RefMonthOfYear r where r.monthOfYearNumber<>?1 and r.monthOfYearShortName=?2")
	Optional<RefMonthOfYear> findByMoyNmAndMoyNr(Long monthOfYearNumber, String monthOfYearShortName);
}
