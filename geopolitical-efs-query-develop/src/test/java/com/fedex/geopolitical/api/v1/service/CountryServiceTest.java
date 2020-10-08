package com.fedex.geopolitical.api.v1.service;

import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedex.geopolitical.api.v1.dto.CountryServiceDTO;
import com.fedex.geopolitical.api.v1.dto.CountryServiceMapperDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.model.CntryOrgStd;
import com.fedex.geopolitical.model.CntryOrgStdPK;
import com.fedex.geopolitical.model.Country;
import com.fedex.geopolitical.model.CountryDialing;
import com.fedex.geopolitical.model.CountryDialingPK;
import com.fedex.geopolitical.model.CountryPK;
import com.fedex.geopolitical.model.Currency;
import com.fedex.geopolitical.model.CurrencyPK;
import com.fedex.geopolitical.model.GeoplAffil;
import com.fedex.geopolitical.model.GeoplAffilPK;
import com.fedex.geopolitical.model.GeoplAffilType;
import com.fedex.geopolitical.model.GeoplHday;
import com.fedex.geopolitical.model.GeoplHdayPK;
import com.fedex.geopolitical.model.GeoplOrgStd;
import com.fedex.geopolitical.model.GeoplUom;
import com.fedex.geopolitical.model.GeoplUomPK;
import com.fedex.geopolitical.model.Geopolitical;
import com.fedex.geopolitical.model.GeopoliticalType;
import com.fedex.geopolitical.model.Holiday;
import com.fedex.geopolitical.model.Locale;
import com.fedex.geopolitical.model.LocalePK;
import com.fedex.geopolitical.model.RefLanguage;
import com.fedex.geopolitical.model.RefScript;
import com.fedex.geopolitical.model.TrnslGeopl;
import com.fedex.geopolitical.model.TrnslGeoplPK;
import com.fedex.geopolitical.repository.CntryDialRepository;
import com.fedex.geopolitical.repository.CntryOrgStdRepository;
import com.fedex.geopolitical.repository.CountryRepository;
import com.fedex.geopolitical.repository.CurrencyRepository;
import com.fedex.geopolitical.repository.GeoplAffilRepository;
import com.fedex.geopolitical.repository.GeoplHdayRepository;
import com.fedex.geopolitical.repository.GeoplUomRepository;
import com.fedex.geopolitical.repository.LocaleRepository;
import com.fedex.geopolitical.repository.TrnslGeoplRepository;
import com.fedex.geopolitical.api.v1.service.impl.CountryServiceV1Impl;
import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.CommonUtilityTest;
import com.fedex.geopolitical.utility.ResponseStatus;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class CountryServiceTest {

	@Autowired
	ObjectMapper objectMapper;

	@InjectMocks
	private CountryServiceV1Impl countryService = new CountryServiceV1Impl();

	@Mock
	CountryRepository countryRepository;

	@Mock
	CntryOrgStdRepository cntryOrgStdRepository;

	@Mock
	CntryDialRepository cntryDialRepository;

	@Mock
	CurrencyRepository currencyRepository;

	@Mock
	GeoplUomRepository geoplUOMRepository;

	@Mock
	GeoplHdayRepository geoplHdayRepository;

	@Mock
	GeoplAffilRepository geoplAffilRepository;

	@Mock
	LocaleRepository localeRepository;

	@Mock
	TrnslGeoplRepository trnslGeoplRepository;

	@Test
	public void testGetCountry() throws ParseException {

		String id = "1";
		Date effectiveDate = Date.valueOf("2018-06-12");
		Date expirationDate = Date.valueOf("9999-12-31");

		countryService.setCntryDialRepository(cntryDialRepository);
		countryService.setCntryOrgStdRepository(cntryOrgStdRepository);
		countryService.setCountryRepository(countryRepository);
		countryService.setCurrencyRepository(currencyRepository);
		countryService.setGeoplAffilRepository(geoplAffilRepository);
		countryService.setGeoplHdayRepository(geoplHdayRepository);
		countryService.setGeoplUOMRepository(geoplUOMRepository);
		countryService.setLocaleRepository(localeRepository);
		countryService.setTrnslGeoplRepository(trnslGeoplRepository);

		when(cntryDialRepository.findByGeopoliticalId(id, effectiveDate, expirationDate))
				.thenReturn(getCountryDialings(id));
		when(cntryOrgStdRepository.findByGeopoliticalId(id, effectiveDate, expirationDate))
				.thenReturn(getCntryOrgStds(id));
		when(currencyRepository.findByGeopoliticalId(id, effectiveDate, expirationDate)).thenReturn(getCurrencies(id));
		when(geoplAffilRepository.findByGeopoliticalId(id, effectiveDate, expirationDate))
				.thenReturn(getGeoplAffils(id));
		when(geoplHdayRepository.findByGeopoliticalId(id, effectiveDate, expirationDate)).thenReturn(getGeoplHdays(id));
		when(geoplUOMRepository.findByGeopoliticalId(id, effectiveDate, expirationDate)).thenReturn(getGeoplUoms(id));
		when(localeRepository.findByGeopoliticalId(id, effectiveDate, expirationDate)).thenReturn(getLocales(id));
		when(trnslGeoplRepository.findByGeopoliticalId(id, effectiveDate, expirationDate))
				.thenReturn(getTrnslGeopls(id));

		CountryServiceMapperDTO countryServiceMapperDTO = new CountryServiceMapperDTO();
		countryServiceMapperDTO.setCountryCd("US");
		countryServiceMapperDTO.setCountryShortName("USA");
		countryServiceMapperDTO.setGeopoliticalId(1l);
		countryServiceMapperDTO.setOrgStandardCode("ISO");
		countryServiceMapperDTO.setEffectiveDate(effectiveDate);
		countryServiceMapperDTO.setExpirationDate(expirationDate);

		List<CountryServiceDTO> countryServiceDTO = new ArrayList<>();
		Optional<Country> countryFirst = getCountry(id);
		countryServiceDTO.add(new CountryServiceDTO(countryFirst.get(), countryServiceMapperDTO));
		
		MetaDTO metaResponse = new MetaDTO();
		MessageDTO message = new MessageDTO();
		metaResponse.setVersion("1.0.0");
		metaResponse.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaResponse.setStatusCode("200");
		message.setStatus(ResponseStatus.SUCCESS);
		message.setInternalMessage(GenericConstants.CONSTANT_INTERNAL_MSG);
		metaResponse.setMessage(message);

		QueryServiceResponseDTO queryServiceResponse = countryService.search(countryServiceMapperDTO);
		queryServiceResponse.setMeta(metaResponse);
		QueryServiceResponseDTO prepareResponse = countryService.prepareReponse(countryService.map(countryServiceDTO));

		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);
		Assert.assertEquals(prepareResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);

		queryServiceResponse = countryService.prepareReponse(countryService.map(countryServiceDTO));
		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);
	}

	private Optional<List<CountryDialing>> getCountryDialings(String geopoliticalId) {
		List<CountryDialing> list = new ArrayList<>();
		CountryDialing countryDialing = new CountryDialing();
		countryDialing.setIntialDialingCd("011");
		countryDialing.setLandPhMaxLthNbr(BigInteger.valueOf(9));
		countryDialing.setLandPhMinLthNbr(BigInteger.valueOf(9));
		countryDialing.setMoblPhMaxLthNbr(BigInteger.valueOf(9));
		countryDialing.setMoblPhMinLthNbr(BigInteger.valueOf(9));
		countryDialing.setExpirationDate(Date.valueOf("9999-12-31"));
		countryDialing
				.setId(new CountryDialingPK(geopoliticalId, "+91", Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		list.add(countryDialing);
		return Optional.of(list);
	}

	private Optional<List<TrnslGeopl>> getTrnslGeopls(String geopoliticalId) {
		List<TrnslGeopl> list = new ArrayList<>();
		TrnslGeopl trnslGeopl = new TrnslGeopl();
		RefLanguage refLanguage = new RefLanguage();
		refLanguage.setEngLanguageNm("ENG");
		trnslGeopl.setTranslationName("United States of America");
		trnslGeopl.setVersionNumber("1");
		trnslGeopl.setVersionDate(Date.valueOf("2020-06-13"));
		trnslGeopl.setExpirationDate(Date.valueOf("9999-12-31"));
		trnslGeopl.setId(
				new TrnslGeoplPK(geopoliticalId, "Latn", Date.valueOf(CommonUtilityTest.getCurrenctDate()), "GE"));

		RefScript script = new RefScript();
		script.setScrptCd("Latn");
		script.setScrptNm("AZZZ");
		script.setScrptDesc("description");
		trnslGeopl.setRefScript(script);

		list.add(trnslGeopl);
		return Optional.of(list);
	}

	private Optional<List<Locale>> getLocales(String geopoliticalId) {
		List<Locale> list = new ArrayList<>();
		Locale locale = new Locale();
		RefLanguage refLanguage = new RefLanguage();
		refLanguage.setEngLanguageNm("ENG");
		locale.setRefLanguage(refLanguage);
		locale.setCldrVersionNumber("1.1");
		locale.setCldrVersionDate(Date.valueOf("2020-06-13"));
		locale.setDateFullFormatDescription("XYZ");
		locale.setDateLongFormatDescription("XYZ");
		locale.setDateMediumFormatDescription("XYZ");
		locale.setDateShortFormatDescription("XYZ");
		locale.setGeopoliticalId(geopoliticalId);
		locale.setExpirationDate(Date.valueOf("9999-12-31"));
		locale.setId(new LocalePK("EN", Date.valueOf(CommonUtilityTest.getCurrenctDate())));

		RefScript script = new RefScript();
		script.setScrptCd("Latn");
		script.setScrptNm("AZZZ");
		script.setScrptDesc("description");
		locale.setRefScript(script);

		list.add(locale);
		return Optional.of(list);
	}

	private Optional<List<GeoplUom>> getGeoplUoms(String geopoliticalId) {
		List<GeoplUom> list = new ArrayList<>();
		GeoplUom geoplUom = new GeoplUom();
		geoplUom.setExpirationDate(Date.valueOf("9999-12-31"));
		geoplUom.setId(new GeoplUomPK("WT", geopoliticalId, Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		list.add(geoplUom);
		return Optional.of(list);
	}

	private Optional<List<Currency>> getCurrencies(String geopoliticalId) {
		List<Currency> list = new ArrayList<>();
		Currency currency = new Currency();
		currency.setCurrencyCd("USD");
		currency.setMinorUnitCd(BigInteger.valueOf(11l));
		currency.setMoneyFormatDescription("USD");
		currency.setExpirationDate(Date.valueOf("9999-12-31"));
		currency.setId(new CurrencyPK(geopoliticalId, BigInteger.valueOf(11l),
				Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		list.add(currency);
		return Optional.of(list);
	}

	private Optional<List<GeoplAffil>> getGeoplAffils(String geopoliticalId) {
		List<GeoplAffil> list = new ArrayList<>();
		GeoplAffil geoplAffil = new GeoplAffil();
		GeoplAffilType geoplAffilType = new GeoplAffilType();
		geoplAffilType.setAffilTypeName("Name");
		geoplAffil.setGeoplAffilType(geoplAffilType);
		geoplAffil.setExpirationDate(Date.valueOf("9999-12-31"));
		geoplAffil.setId(new GeoplAffilPK(1l, geopoliticalId, Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		list.add(geoplAffil);
		return Optional.of(list);
	}

	private Optional<List<GeoplHday>> getGeoplHdays(String geopoliticalId) {
		List<GeoplHday> list = new ArrayList<>();
		GeoplHday geoplHday = new GeoplHday();
		geoplHday.setExpirationDate(Date.valueOf("9999-12-31"));
		Holiday holiday = new Holiday();
		holiday.setHolidayName("Hallowen");
		geoplHday.setHoliday(holiday);
		geoplHday.setId(new GeoplHdayPK(geopoliticalId, 1l, Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		list.add(geoplHday);
		return Optional.of(list);
	}

	private Optional<List<CntryOrgStd>> getCntryOrgStds(String geopoliticalId) {
		List<CntryOrgStd> list = new ArrayList<>();
		CntryOrgStd cntryOrgStd = new CntryOrgStd();
		GeoplOrgStd geoplOrgStd = new GeoplOrgStd();
		geoplOrgStd.setOrgStdNm("USA-Name");
		cntryOrgStd.setGeoplOrgStd(geoplOrgStd);
		cntryOrgStd.setCntryFullNm("USA");
		cntryOrgStd.setCntryShtNm("USA");
		cntryOrgStd.setExpirationDate(Date.valueOf("9999-12-31"));
		cntryOrgStd.setId(new CntryOrgStdPK(geopoliticalId, "ISO", Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		list.add(cntryOrgStd);
		return Optional.of(list);
	}

	private Optional<Country> getCountry(String id) throws ParseException {
		Country country = new Country();
		country.setId(new CountryPK(id, Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		country.setCountryNumberCd(BigInteger.valueOf(2));
		country.setCountryCd("US");
		country.setThreeCharCountryCd("USA");
		country.setIndependentFlag("1");
		country.setPostalFormatDescription("USA");
		country.setPostalFlag("1");
		country.setPostalLengthNumber(BigInteger.valueOf(6));
		country.setFirstWorkWeekDayName("Monday");
		country.setLastWorkWeekDayName("Monday");
		country.setWeekendFirstDayName("Monday");
		country.setInternetDomainName("Us");
		country.setDependentRelationshipId("1l");
		country.setDependentCountryCd(BigInteger.valueOf(91));
		country.setExpirationDate(Date.valueOf("9999-12-31"));

		/*
		 * List<Geopolitical> geopoliticals = new ArrayList<>(); Geopolitical
		 * geopolitical = new Geopolitical(); GeopoliticalType geopoliticalType = new
		 * GeopoliticalType(); geopoliticalType.setGeopoliticalTypeName("560037");
		 * geopolitical.setGeopoliticalType(geopoliticalType);
		 * geopoliticals.add(geopolitical); country.setGeopoliticals(geopoliticals);
		 */

		return Optional.of(country);
	}

}
