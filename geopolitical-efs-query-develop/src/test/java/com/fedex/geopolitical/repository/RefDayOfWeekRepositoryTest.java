package com.fedex.geopolitical.repository;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fedex.geopolitical.model.RefDayOfWeek;
import com.fedex.geopolitical.repository.RefDayOfWeekRepository;
import com.fedex.geopolitical.utility.CommonUtilityTest;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RefDayOfWeekRepositoryTest {
	
	@Autowired
	RefDayOfWeekRepository repository;
	
	@Test
	public void test()
	{
		Optional<RefDayOfWeek> requestObj = getRefDayOfWeek("CHRST");
		repository.save(requestObj.get());
		
		Optional<RefDayOfWeek> result = repository.findById(10l);
		Assert.assertEquals(true,result.isPresent());
		Assert.assertEquals(requestObj.get().getDayOfWeekNumber(), result.get().getDayOfWeekNumber());
		Assert.assertEquals(requestObj.get().getDayOfWeekShortName(), result.get().getDayOfWeekShortName());
		
		repository.delete(requestObj.get());
		result = repository.findById(10l);
		Assert.assertEquals(false,result.isPresent());
		
	}

	private Optional<RefDayOfWeek> getRefDayOfWeek(String code)
	{
		RefDayOfWeek objType = new RefDayOfWeek();
		objType.setDayOfWeekNumber(10l);
		objType.setDayOfWeekShortName(code);
		objType.setCreatedByUserId("3766268");
		objType.setCreatedTmstp(CommonUtilityTest.getCurrentTimeStamp());
		objType.setLastUpdatedByUserId("3766268");
		objType.setLastUpdatedTmstp(CommonUtilityTest.getCurrentTimeStamp());;
		return Optional.of(objType);
	}
	
}
