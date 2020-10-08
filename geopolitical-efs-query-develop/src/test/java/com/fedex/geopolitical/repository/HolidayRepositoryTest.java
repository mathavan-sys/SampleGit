package com.fedex.geopolitical.repository;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fedex.geopolitical.model.Holiday;
import com.fedex.geopolitical.utility.CommonUtilityTest;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class HolidayRepositoryTest {
	
	@Autowired
	HolidayRepository repository;
	
	@Test
	public void test()
	{
		Optional<Holiday> requestObj = getHoliday("CHRST");
		repository.save(requestObj.get());
		
		Optional<Holiday> result = repository.findById((10l));
		Assert.assertEquals(true,result.isPresent());
		Assert.assertEquals(requestObj.get().getHolidayId(), result.get().getHolidayId());
		Assert.assertEquals(requestObj.get().getHolidayName(), result.get().getHolidayName());
		
		repository.delete(requestObj.get());
		result = repository.findById((10l));
		Assert.assertEquals(false,result.isPresent());
		
	}

	private Optional<Holiday> getHoliday(String code)
	{
		Holiday objType = new Holiday();
		objType.setHolidayId((10l));
		objType.setHolidayName(code);
		objType.setCreatedByUserId("3766268");
		objType.setCreatedTmstp(CommonUtilityTest.getCurrentTimeStamp());
		objType.setLastUpdatedByUserId("3766268");
		objType.setLastUpdatedTmstp(CommonUtilityTest.getCurrentTimeStamp());;
		return Optional.of(objType);
	}
	
}
