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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedex.geopolitical.dto.User;
import com.fedex.geopolitical.model.Country;
import com.fedex.geopolitical.model.CountryPK;
import com.fedex.geopolitical.model.Geopolitical;
import com.fedex.geopolitical.model.GeopoliticalPK;
import com.fedex.geopolitical.model.GeopoliticalType;
import com.fedex.geopolitical.utility.CommonUtilityTest;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GeopoliticalRepositoryTest {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	GeopoliticalRepository geoplRepository;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	GeopoliticalTypeRepository geopoliticalTypeRepository;

	@Test
	public void test() {
		User user = new User();
		user.setName("3766273");

		Country country = new Country();
		country.setId(new CountryPK("1", Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		country.setCountryCd("IN");
		country.setCountryNumberCd(BigInteger.valueOf(1l));
		country.setThreeCharCountryCd("abc");
		country.setCreatedByUserId("37663271");
		country.setLastUpdatedByNm("37663271");
		countryRepository.save(country);

		GeopoliticalType geopoliticalType = new GeopoliticalType();
		geopoliticalType.setGeopoliticalTypeId((10l));
		geopoliticalType.setGeopoliticalTypeName("abc");
		geopoliticalType.setCreatedByUserId("3766271");
		geopoliticalType.setLastUpdatedByUserId("3766271");
		geopoliticalTypeRepository.save(geopoliticalType);

		Geopolitical geo = new Geopolitical();
		GeopoliticalPK pk = new GeopoliticalPK();
		pk.setGeopoliticalId("1");
		pk.setEffectiveDate(Date.valueOf(CommonUtilityTest.getCurrenctDate()));
		geo.setId(pk);
		geo.setCreatedByUserId(user.getName());
		geo.setLastUpdatedByNm(user.getName());
		geo.setCreatedTmstp(CommonUtilityTest.getCurrentTimeStamp());
		geo.setLastUpdatedTmstp(CommonUtilityTest.getCurrentTimeStamp());
		geo.setExpirationDate(Date.valueOf(CommonUtilityTest.getCurrenctDate()));
		geo.setCountry(country);
		geo.setGeopoliticalTypeId(10l);
		geo.setGeopoliticalType(geopoliticalType);
		geoplRepository.save(geo);

		Optional<Geopolitical> result = geoplRepository
				.findById(new GeopoliticalPK("1", Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		Assert.assertEquals(true, result.isPresent());
		Assert.assertEquals(geo.getId().getGeopoliticalId(), result.get().getId().getGeopoliticalId());
		Assert.assertEquals(geo.getGeopoliticalTypeId(), result.get().getGeopoliticalTypeId());

	}

}
