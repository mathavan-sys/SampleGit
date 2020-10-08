package com.fedex.geopolitical.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.RefDayOfWeek;

@Repository
public interface RefDayOfWeekRepository extends JpaRepository<RefDayOfWeek, Long>{
	
	Optional<RefDayOfWeek> findByDayOfWeekShortName(String dayOfWeekShortName);

	Optional<RefDayOfWeek> findByDayOfWeekFullName(String dayOfWeekFullName);
	Optional<RefDayOfWeek> findByDayOfWeekNumber(long dayOfWeekNumber);
	
	@Query("select r from RefDayOfWeek r where r.dayOfWeekNumber<>?1 and r.dayOfWeekShortName=?2")
	Optional<RefDayOfWeek> findByDowNmAndDowNr(Long dayOfWeekNumber, String dayOfWeekShortName);
	
	@Query("select r from RefDayOfWeek r where r.dayOfWeekNumber<>?1 and r.dayOfWeekFullName=?2")
	Optional<RefDayOfWeek> findByDowFullNmAndDowNr(Long dayOfWeekNumber, String dayOfWeekFullName);
	
}
