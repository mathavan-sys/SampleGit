package com.fedex.geopolitical.repository;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fedex.geopolitical.model.Country;
import com.fedex.geopolitical.model.CountryPK;
import com.fedex.geopolitical.utility.CommonUtilityTest;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CountryRepositoryTest {
	
	@Autowired
	CountryRepository countryRepository;
	
	@Test
	public void test()
	{
		Country country = new Country();
		country.setId(new CountryPK("1", Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		country.setCountryCd("IN");
		country.setCountryNumberCd(BigInteger.valueOf(1l));
		country.setThreeCharCountryCd("abc");
		country.setCreatedByUserId("37663271");
		country.setLastUpdatedByNm("37663271");
		countryRepository.save(country);
		
		Optional<Country> result = countryRepository.findById(new CountryPK("1", Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		Assert.assertEquals(true,result.isPresent());
		Assert.assertEquals(country.getId().getGeopoliticalId(), result.get().getId().getGeopoliticalId());
		Assert.assertEquals(country.getCountryCd(), result.get().getCountryCd());
		
	}
	
}
