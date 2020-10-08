package com.fedex.geopolitical.repository;

import java.sql.Date;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fedex.geopolitical.model.CountryDialing;
import com.fedex.geopolitical.model.CountryDialingPK;
import com.fedex.geopolitical.utility.CommonUtilityTest;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CountryDialingRepositoryTest {

	@Autowired
	CntryDialRepository cntryDialRepository;

	@Autowired
	CountryRepository countryRepository;

	@Before
	public void setup() {
		CountryDialing countryDialing = getCountryDialing("011", "+91", "1");
		cntryDialRepository.save(countryDialing);
	}

	@Test
	public void test() {
		CountryDialing countryDialing = getCountryDialing("011", "+91", "1");

		Optional<CountryDialing> result = cntryDialRepository
				.findById(new CountryDialingPK("1", "+91", Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		Assert.assertEquals(true, result.isPresent());
		Assert.assertEquals(countryDialing.getId().getGeopoliticalId(), result.get().getId().getGeopoliticalId());
		Assert.assertEquals(countryDialing.getId().getIntialDialingPrefixCd(),
				result.get().getId().getIntialDialingPrefixCd());

		cntryDialRepository.delete(countryDialing);
		result = cntryDialRepository
				.findById(new CountryDialingPK("1", "+91", Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		Assert.assertEquals(false, result.isPresent());
	}

	private CountryDialing getCountryDialing(String intialDialingCd, String intialDialingPrefixCd,
			String geopoliticalId) {
		CountryDialing countryDialing = new CountryDialing();
		CountryDialingPK countryDialingPK = new CountryDialingPK(geopoliticalId, intialDialingPrefixCd,
				Date.valueOf(CommonUtilityTest.getCurrenctDate()));
		countryDialing.setId(countryDialingPK);
		countryDialing.setIntialDialingCd(intialDialingCd);
		countryDialing.setCreatedByUserId("37663271");
		countryDialing.setLastUpdatedByNm("37663271");
		return countryDialing;
	}

}
