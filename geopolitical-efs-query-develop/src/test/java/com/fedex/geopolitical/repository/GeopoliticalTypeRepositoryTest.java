package com.fedex.geopolitical.repository;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fedex.geopolitical.model.GeopoliticalType;
import com.fedex.geopolitical.repository.GeopoliticalTypeRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GeopoliticalTypeRepositoryTest {
	
	@Autowired
	GeopoliticalTypeRepository geopoliticalTypeRepository;
	
	@Test
	public void test()
	{
		GeopoliticalType geopoliticalType = getGeopoliticalType("Christmas");
		geopoliticalTypeRepository.save(geopoliticalType);
		
		Optional<GeopoliticalType> result = geopoliticalTypeRepository.findById((10l));
		Assert.assertEquals(true,result.isPresent());
		Assert.assertEquals(geopoliticalType.getGeopoliticalTypeId(), result.get().getGeopoliticalTypeId());
		Assert.assertEquals(geopoliticalType.getGeopoliticalTypeName(), result.get().getGeopoliticalTypeName());

		result = geopoliticalTypeRepository.findById((20l));
		Assert.assertEquals(false,result.isPresent());
		
	}

	private GeopoliticalType getGeopoliticalType(String geopoliticalTypeName)
	{
		GeopoliticalType geopoliticalType = new GeopoliticalType();
		geopoliticalType.setGeopoliticalTypeId((10l));
		geopoliticalType.setGeopoliticalTypeName(geopoliticalTypeName);
		geopoliticalType.setCreatedByUserId("3766271");
		geopoliticalType.setLastUpdatedByUserId("3766271");
		geopoliticalType.setCreatedTmstp(null);
		geopoliticalType.setGeopoliticals(null);
		geopoliticalType.setLastUpdatedTmstp(null);
		return geopoliticalType;
	}
	
}
