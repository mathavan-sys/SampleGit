package com.fedex.geopolitical.repository;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fedex.geopolitical.model.GeopoliticalRelationshipType;
import com.fedex.geopolitical.repository.GeopoliticalRelationshipTypeRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode=ClassMode.BEFORE_CLASS)
public class GeopoliticalRelationshipTypeRepositoryTest {
	
	@Autowired
	GeopoliticalRelationshipTypeRepository geopoliticalRelationshipTypeRepository;
	
	@Test
	public void test()
	{
		GeopoliticalRelationshipType geopoliticalType = getGeopoliticalRelationshipType("Christmas");
		geopoliticalRelationshipTypeRepository.save(geopoliticalType);
		
		Optional<GeopoliticalRelationshipType> result = geopoliticalRelationshipTypeRepository.findById("10");
		Assert.assertEquals(true,result.isPresent());
		Assert.assertEquals(geopoliticalType.getGeopoliticalRelationshipTypeCd(), result.get().getGeopoliticalRelationshipTypeCd());
		Assert.assertEquals(geopoliticalType.getAreaRelationshipTypeDescription(), result.get().getAreaRelationshipTypeDescription());
		
		geopoliticalRelationshipTypeRepository.delete(geopoliticalType);
		result = geopoliticalRelationshipTypeRepository.findById("10");
		Assert.assertEquals(false,result.isPresent());
		
	}
	
	private GeopoliticalRelationshipType getGeopoliticalRelationshipType(String geopoliticalRelationTypeName)
	{
		GeopoliticalRelationshipType geopoliticalRelationshipType = new GeopoliticalRelationshipType();
		geopoliticalRelationshipType.setGeopoliticalRelationshipTypeCd("10");
		geopoliticalRelationshipType.setAreaRelationshipTypeDescription(geopoliticalRelationTypeName);
		geopoliticalRelationshipType.setCreatedByUserId("3766271");
		geopoliticalRelationshipType.setLastUpdatedByNm("3766271");
		return geopoliticalRelationshipType;
	}
	
}
