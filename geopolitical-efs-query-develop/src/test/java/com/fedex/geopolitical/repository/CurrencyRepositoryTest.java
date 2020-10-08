package com.fedex.geopolitical.repository;

import java.math.BigInteger;
import java.sql.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fedex.geopolitical.model.Currency;
import com.fedex.geopolitical.model.CurrencyPK;
import com.fedex.geopolitical.repository.CountryRepository;
import com.fedex.geopolitical.repository.CurrencyRepository;
import com.fedex.geopolitical.utility.CommonUtilityTest;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CurrencyRepositoryTest {

	@Autowired
	CurrencyRepository currencyRepository;

	@Autowired
	CountryRepository countryRepository;

	@Before
	public void setup() {
		Currency currency = getCurrency("RS", BigInteger.valueOf(11l), "1", BigInteger.valueOf(11l));
		Set<Currency> currencies = new HashSet<>();
		currencies.add(currency);
		currencyRepository.save(currency);
	}

	@Test
	public void test() {
		Currency countryDialing = getCurrency("RS", BigInteger.valueOf(11l), "1", BigInteger.valueOf(11l));

		Optional<Currency> result = currencyRepository.findById(
				new CurrencyPK("1", BigInteger.valueOf(11l), Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		Assert.assertEquals(true, result.isPresent());
		Assert.assertEquals(countryDialing.getId().getGeopoliticalId(), result.get().getId().getGeopoliticalId());
		Assert.assertEquals(countryDialing.getId().getCurrencyNumberCd(), result.get().getId().getCurrencyNumberCd());

		currencyRepository.delete(countryDialing);
		result = currencyRepository.findById(
				new CurrencyPK("1", BigInteger.valueOf(11l), Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		Assert.assertEquals(false, result.isPresent());
	}

	private Currency getCurrency(String currencyCd, BigInteger minorUnitCd, String geopoliticalId,
			BigInteger currencyNumberCd) {
		Currency currency = new Currency();
		CurrencyPK pk = new CurrencyPK(geopoliticalId, currencyNumberCd,
				Date.valueOf(CommonUtilityTest.getCurrenctDate()));
		currency.setId(pk);
		currency.setCurrencyCd(currencyCd);
		currency.setMinorUnitCd(minorUnitCd);
		return currency;
	}

}
