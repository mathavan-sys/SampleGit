/*package com.fedex.geopolitical.repository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fedex.geopolitical.model.Country;
import com.fedex.geopolitical.model.Geopolitical;
import com.fedex.geopolitical.model.Locale;

import junit.framework.Assert;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LocaleRepositoryTest {

	@Autowired
	LocaleRepository localeRepository;

	@Autowired
	CountryRepository countryRepository;

	@Before
	public void setup(){
		Country country = getCountry("IN", new BigDecimal(2l), "abc");
		Geopolitical geopolitical = getGeopolitical(1l, 11l);
		Locale locale = getLocale("EN", "EN", 1l);
		Set<Locale> locales = new HashSet<>();
		locales.add(locale);
		geopolitical.setLocales(locales);
		country.setGeopolitical(geopolitical);
		countryRepository.save(country);
	}

	@Test
	public void test()
	{
		Locale locale = getLocale("EN", "EN", 1l);

		Optional<Locale> result = localeRepository.findByGeopoliticalId(2434343434343);
		Assert.assertEquals(true,result.isPresent());
		Assert.assertEquals(locale.getGeopoliticalId(), result.get().getGeopoliticalId());
		Assert.assertEquals(locale.getLanguageCd(), result.get().getLanguageCd());
		
		localeRepository.delete(locale);
		result = localeRepository.findByGeopoliticalId(2434343434343);
		Assert.assertEquals(false,result.isPresent());
	}

	private Geopolitical getGeopolitical(String geopoliticalId, long geopoliticalTypeId)
	{
		Geopolitical geopolitical = new Geopolitical();
		geopolitical.getId().setGeopoliticalId(geopoliticalId);
		geopolitical.setGeopoliticalTypeId(geopoliticalTypeId);
		return geopolitical;
	}
	
	
	
	private Locale getLocale(String localeCd, String languageCd, String geopoliticalId)
	{
		Locale locale = new Locale();
		locale.setGeopoliticalId(geopoliticalId);
		locale.setLanguageCd(languageCd);
		return locale;
	}

}
*/