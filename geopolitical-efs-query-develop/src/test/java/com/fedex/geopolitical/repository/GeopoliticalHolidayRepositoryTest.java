package com.fedex.geopolitical.repository;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fedex.geopolitical.model.GeoplHday;
import com.fedex.geopolitical.model.GeoplHdayPK;
import com.fedex.geopolitical.model.Holiday;
import com.fedex.geopolitical.repository.CountryRepository;
import com.fedex.geopolitical.repository.GeoplHdayRepository;
import com.fedex.geopolitical.repository.HolidayRepository;
import com.fedex.geopolitical.utility.CommonUtilityTest;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GeopoliticalHolidayRepositoryTest {

	@Autowired
	GeoplHdayRepository geoplHdayRepository;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	HolidayRepository holidayRepository;

	@Before
	public void setup() {
		Holiday holiday = getHoliday("Diwali");
		holidayRepository.save(holiday);
		GeoplHday geoplHday = getGeopoliticalHoliday((11l), "1");
		Set<GeoplHday> geopoliticalHolidays = new HashSet<>();
		geopoliticalHolidays.add(geoplHday);
		geoplHdayRepository.save(geoplHday);
	}

	@Test
	public void test() {
		GeoplHday geoplHday = getGeopoliticalHoliday((11l), "1");

		Optional<GeoplHday> result = geoplHdayRepository
				.findById(new GeoplHdayPK("1", (11l), Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		Assert.assertEquals(true, result.isPresent());
		Assert.assertEquals(geoplHday.getId().getGeopoliticalId(), result.get().getId().getGeopoliticalId());
		Assert.assertEquals(geoplHday.getId().getHolidayId(), result.get().getId().getHolidayId());

		geoplHdayRepository.delete(geoplHday);
		result = geoplHdayRepository.findById(new GeoplHdayPK("1", (11l), Calendar.getInstance().getTime()));
		Assert.assertEquals(false, result.isPresent());
	}

	private GeoplHday getGeopoliticalHoliday(long holidayId, String geopoliticalId) {
		GeoplHday geoplHday = new GeoplHday();
		GeoplHdayPK pk = new GeoplHdayPK(geopoliticalId, holidayId, Date.valueOf(CommonUtilityTest.getCurrenctDate()));
		geoplHday.setId(pk);
		return geoplHday;
	}

	private Holiday getHoliday(String code) {
		Holiday holiday = new Holiday();
		holiday.setHolidayId((11l));
		holiday.setHolidayName(code);
		return holiday;
	}

}
