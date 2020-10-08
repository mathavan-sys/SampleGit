package com.fedex.geopolitical.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.Holiday;


@Repository
public interface HolidayRepository extends JpaRepository<Holiday,Long> {
	
	Optional<Holiday> findByHolidayName(String holidayName);
	
	Optional<Holiday> findByHolidayDateParamText(String holidayDateParamText);
	
	@Query("select h from Holiday h where h.holidayId<>?1 and h.holidayName=?2")
	Optional<Holiday> findByHdayNmAndNotHdayId(long holidayId, String holidayName);
	
	@Query("select h from Holiday h where h.holidayId<>?1 and h.holidayDateParamText=?2")
	Optional<Holiday> findByHolidayDateParamTextAndNotHdayId(long holidayId, String holidayDateParamText);
	
}
