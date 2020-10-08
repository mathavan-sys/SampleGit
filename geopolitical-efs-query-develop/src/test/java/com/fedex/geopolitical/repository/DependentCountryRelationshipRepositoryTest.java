package com.fedex.geopolitical.repository;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fedex.geopolitical.model.DependentCountryRelationship;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DependentCountryRelationshipRepositoryTest {
	
	@Autowired
	DependentCountryRelationshipRepository dependentCountryRelationshipRepository;
	
	@Test
	public void test()
	{
		DependentCountryRelationship dependentCountryRelationship = getDependentCountryRelationship("Christmas");
		dependentCountryRelationshipRepository.save(dependentCountryRelationship);
		
		Optional<DependentCountryRelationship> result = dependentCountryRelationshipRepository.findById(dependentCountryRelationship.getDependentRelationshipId());
		Assert.assertEquals(true, result.isPresent());
		Assert.assertEquals(dependentCountryRelationship.getDependentRelationshipId(), result.get().getDependentRelationshipId());
		Assert.assertEquals(dependentCountryRelationship.getDependentRelationshipDescription(), result.get().getDependentRelationshipDescription());
		
		dependentCountryRelationshipRepository.delete(dependentCountryRelationship);
		result = dependentCountryRelationshipRepository.findById(dependentCountryRelationship.getDependentRelationshipId());
		Assert.assertEquals(false, result.isPresent());
	}
	
	private DependentCountryRelationship getDependentCountryRelationship(String dependentRelationshipDesc)
	{
		DependentCountryRelationship dependentCountryRelationship = new DependentCountryRelationship();
		dependentCountryRelationship.setDependentRelationshipId("CS");
		dependentCountryRelationship.setDependentRelationshipDescription(dependentRelationshipDesc);
		dependentCountryRelationship.setCreatedByUserId("37663271");
		dependentCountryRelationship.setLastUpdatedByUserId("37663271");
		return dependentCountryRelationship;
	}
	
}
