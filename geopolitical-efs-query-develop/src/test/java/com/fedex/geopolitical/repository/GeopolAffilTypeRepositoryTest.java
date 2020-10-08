package com.fedex.geopolitical.repository;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fedex.geopolitical.model.GeoplAffilType;
import com.fedex.geopolitical.utility.CommonUtilityTest;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GeopolAffilTypeRepositoryTest {
	
	@Autowired
	GeoplAffilTypeRepository repository;
	
	@Test
	public void test()
	{
		Optional<GeoplAffilType> requestObj = getGeoplAffilType("CHRST");
		repository.save(requestObj.get());
		
		Optional<GeoplAffilType> result = repository.findById(10l);
		Assert.assertEquals(true,result.isPresent());
		Assert.assertEquals(requestObj.get().getAffilTypeId(), result.get().getAffilTypeId());
		Assert.assertEquals(requestObj.get().getAffilTypeCode(), result.get().getAffilTypeCode());
		
		repository.delete(requestObj.get());
		result = repository.findById(10l);
		Assert.assertEquals(false,result.isPresent());
		
	}

	private Optional<GeoplAffilType> getGeoplAffilType(String code)
	{
		GeoplAffilType affilType = new GeoplAffilType();
		affilType.setAffilTypeId(10l);
		affilType.setAffilTypeCode(code);
		affilType.setCreatedByUserId("3766268");
		affilType.setCreatedTmstp(CommonUtilityTest.getCurrentTimeStamp());
		affilType.setLastUpdatedByUserId("3766268");
		affilType.setLastUpdatedTmstp(CommonUtilityTest.getCurrentTimeStamp());;
		return Optional.of(affilType);
	}
	
}
