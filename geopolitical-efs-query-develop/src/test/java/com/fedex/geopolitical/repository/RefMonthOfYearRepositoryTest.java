/*package com.fedex.geopolitical.repository;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fedex.geopolitical.model.RefMonthOfYear;
import com.fedex.geopolitical.utility.CommonUtilityTest;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RefMonthOfYearRepositoryTest {
	
	@Autowired
	RefMonthOfYearRepository repository;
	
	@Test
	public void test()
	{
		Optional<RefMonthOfYear> requestObj = getRefMonthOfYear("CHRST");
		repository.save(requestObj.get());
		
		Optional<RefMonthOfYear> result = repository.findById(10l);
		Assert.assertEquals(true,result.isPresent());
		Assert.assertEquals(requestObj.get().getMonthOfYearNumber(), result.get().getMonthOfYearNumber());
		Assert.assertEquals(requestObj.get().getMonthOfYearShortName(), result.get().getMonthOfYearShortName());
		
		repository.delete(requestObj.get());
		result = repository.findById(10l);
		Assert.assertEquals(false,result.isPresent());
		
	}

	private Optional<RefMonthOfYear> getRefMonthOfYear(String code)
	{
		RefMonthOfYear objType = new RefMonthOfYear();
		objType.setMonthOfYearNumber(10l);
		objType.setMonthOfYearShortName(code);
		objType.setCreatedByUserId("3766268");
		objType.setCreatedTmstp(CommonUtilityTest.getCurrentTimeStamp());
		objType.setLastUpdatedByUserId("3766268");
		objType.setLastUpdatedTmstp(CommonUtilityTest.getCurrentTimeStamp());;
		return Optional.of(objType);
	}
	
}
*/