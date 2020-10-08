package com.fedex.geopolitical.service;

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
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fedex.geopolitical.dto.CountryServiceDTO;
import com.fedex.geopolitical.dto.CountryServiceMapperDTO;
import com.fedex.geopolitical.dto.CurrencyDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.model.AddressLabel;
import com.fedex.geopolitical.model.AddressLabelPK;
import com.fedex.geopolitical.model.CntryOrgStd;
import com.fedex.geopolitical.model.CntryOrgStdPK;
import com.fedex.geopolitical.model.Country;
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
import com.fedex.geopolitical.model.Holiday;
import com.fedex.geopolitical.model.Locale;
import com.fedex.geopolitical.model.LocalePK;
import com.fedex.geopolitical.model.RefLanguage;
import com.fedex.geopolitical.model.RefScript;
import com.fedex.geopolitical.model.TrnslGeopl;
import com.fedex.geopolitical.model.TrnslGeoplPK;
import com.fedex.geopolitical.repository.AddressLabelRepository;
import com.fedex.geopolitical.repository.CntryOrgStdRepository;
import com.fedex.geopolitical.repository.CountryRepository;
import com.fedex.geopolitical.repository.CurrencyRepository;
import com.fedex.geopolitical.service.impl.CountryServiceImpl;
import com.fedex.geopolitical.utility.CommonUtilityTest;
import com.fedex.geopolitical.utility.ResponseStatus;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class CountryServiceTest {

	@Autowired
	ObjectMapper objectMapper;

	@InjectMocks
	private CountryServiceImpl countryService = new CountryServiceImpl();

	@Mock
	CurrencyDTO currencyDTO;

	@Mock
	CountryRepository countryRepository;

	@Mock
	CntryOrgStdRepository cntryOrgStdRepository;

	@Mock
	CurrencyRepository currencyRepository;

	@Mock
	AddressLabelRepository addressLabelRepository;

	@Test
	public void testGetCountry() throws ParseException {

		String id = "1";
		Date effectiveDate = Date.valueOf("2018-06-12");
		Date expirationDate = Date.valueOf("9999-12-31");

		countryService.setCntryOrgStdRepository(cntryOrgStdRepository);
		countryService.setCountryRepository(countryRepository);
		countryService.setCurrencyRepository(currencyRepository);
		countryService.setAddressLabelRepository(addressLabelRepository);

		Optional<List<Country>> sampleCountryList = Optional.of(new ArrayList<Country>());
		/*
		 * when(countryRepository.findByDate(ArgumentMatchers.any(Date.class),
		 * ArgumentMatchers.any(Date.class))) .thenReturn(sampleCountryList);
		 */

		when(countryRepository.findFirstByCountryCd(ArgumentMatchers.anyString())).thenReturn(getCountry(id));

		List<Country> sampleCountryArray = new ArrayList<Country>();
		sampleCountryArray.add(getCountry(id).get());
		when(countryRepository.findByGeopoliticalIdsAndDate(ArgumentMatchers.anyList(),
				ArgumentMatchers.any(Date.class), ArgumentMatchers.any(Date.class))).thenReturn(sampleCountryArray);
		when(countryRepository.findByGeopoliticalIdAndDate(ArgumentMatchers.anyString(),
				ArgumentMatchers.any(Date.class), ArgumentMatchers.any(Date.class))).thenReturn(sampleCountryList);
		// when(cntryOrgStdRepository.findAll()).thenReturn(getCntryOrgStds(id).get());
		when(cntryOrgStdRepository.findAll(ArgumentMatchers.<Specification<CntryOrgStd>>any()))
				.thenReturn(getCntryOrgStds(id).get());
		when(cntryOrgStdRepository.findByGeopoliticalId(id, effectiveDate, expirationDate))
				.thenReturn(getCntryOrgStds(id));
		when(currencyRepository.findByGeopoliticalId(id, effectiveDate, expirationDate)).thenReturn(getCurrencies(id));
		/*
		 * when(geoplAffilRepository.findByGeopoliticalId(id, effectiveDate,
		 * expirationDate)) .thenReturn(getGeoplAffils(id));
		 * when(geoplHdayRepository.findByGeopoliticalId(id, effectiveDate,
		 * expirationDate)).thenReturn(getGeoplHdays(id));
		 * when(geoplUOMRepository.findByGeopoliticalId(id, effectiveDate,
		 * expirationDate)).thenReturn(getGeoplUoms(id));
		 * when(localeRepository.findByGeopoliticalId(id, effectiveDate,
		 * expirationDate)).thenReturn(getLocales(id));
		 * when(trnslGeoplRepository.findByGeopoliticalId(ArgumentMatchers.
		 * anyString(), ArgumentMatchers.any(Date.class),
		 * ArgumentMatchers.any(Date.class))).thenReturn(getTrnslGeopls(id));
		 */
		when(addressLabelRepository.findByGeopoliticalId(String.valueOf(id), effectiveDate, expirationDate))
				.thenReturn(getAddressLabel(String.valueOf(id)));
		// when(countryRepository.findByDateNew(effectiveDate,
		// expirationDate)).thenReturn(getCountryByDateNew());
		when(addressLabelRepository.findByAllAddressLabels(effectiveDate, expirationDate))
				.thenReturn(getAddressLabelList());
		when(countryRepository.findByDate(ArgumentMatchers.any(Date.class), ArgumentMatchers.any(Date.class)))
				.thenReturn(getCountryList());
		when(cntryOrgStdRepository.findByAllCntryOrgStd(effectiveDate, expirationDate))
				.thenReturn(getAllCntryOrgStds());
		when(currencyRepository.findByAllCurrencies(effectiveDate, expirationDate)).thenReturn(getByAllCurrencies());

		CountryServiceMapperDTO countryServiceMapperDTO = new CountryServiceMapperDTO();
		countryServiceMapperDTO.setCountryCode("US");
		countryServiceMapperDTO.setCountryShortName("USA");
		countryServiceMapperDTO.setGeopoliticalId("1");
		countryServiceMapperDTO.setOrganizationStandardCode("ISO");
		countryServiceMapperDTO.setEffectiveDate(effectiveDate);
		countryServiceMapperDTO.setExpirationDate(expirationDate);

		List<CountryServiceDTO> countryServiceDTO = new ArrayList<>();
		Optional<Country> countryFirst = getCountry(id);
		countryServiceDTO.add(new CountryServiceDTO(countryFirst.get(), countryServiceMapperDTO));

		// find by geo id
		QueryServiceResponseDTO queryServiceResponse = countryService.search(countryServiceMapperDTO);
		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);

		// find by country cd
		countryServiceMapperDTO.setGeopoliticalId(null);

		queryServiceResponse = countryService.search(countryServiceMapperDTO);
		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);

		// find by short name & org std

		countryServiceMapperDTO.setCountryCode(null);
		queryServiceResponse = countryService.search(countryServiceMapperDTO);
		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);

		// find all
		countryServiceMapperDTO.setCountryShortName(null);
		countryServiceMapperDTO.setOrganizationStandardCode(null);
		queryServiceResponse = countryService.search(countryServiceMapperDTO);

		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);

		queryServiceResponse = countryService.prepareReponse(countryService.map(countryServiceDTO));
		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);

	}

	private Optional<List<Country>> getCountryList() {
		List<Country> countryList = new ArrayList<Country>();
		Country country = new Country();
		country.setId(new CountryPK("12345", Date.valueOf(CommonUtilityTest.getCurrenctDate())));
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
		country.setDependentRelationshipId("CS");
		country.setDependentCountryCd(BigInteger.valueOf(91));
		country.setExpirationDate(Date.valueOf("9999-12-31"));
		countryList.add(country);
		return Optional.of(countryList);
	}

	private Optional<List<TrnslGeopl>> getTrnslGeopls(String geopoliticalId) {
		List<TrnslGeopl> list = new ArrayList<>();
		TrnslGeopl trnslGeopl = new TrnslGeopl();
		trnslGeopl.setId(new TrnslGeoplPK(geopoliticalId, "sccd", Date.valueOf("2020-06-13"), "loclcd"));
		RefLanguage refLanguage = new RefLanguage();
		refLanguage.setEngLanguageNm("ENG");
		trnslGeopl.setTranslationName("United States of America");
		trnslGeopl.setVersionNumber("1");
		trnslGeopl.setVersionDate(Date.valueOf("2020-06-13"));
		trnslGeopl.setExpirationDate(Date.valueOf("9999-12-31"));

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
		locale.setCreatedByUserId("A");
		locale.setLangCd("A");
		locale.setLastUpdatedByNm("XYZ");
		locale.setLastUpdatedTmstp(CommonUtilityTest.getCurrentTimeStamp());

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

	private Optional<Country> getCountry(String geopoliticalId) throws ParseException {
		Country country = new Country();
		country.setId(new CountryPK(geopoliticalId, Date.valueOf(CommonUtilityTest.getCurrenctDate())));
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
		country.setDependentRelationshipId("CS");
		country.setDependentCountryCd(BigInteger.valueOf(91));
		country.setExpirationDate(Date.valueOf("9999-12-31"));

		/*
		 * List<Geopolitical> geopoliticals = new ArrayList<>(); Geopolitical
		 * geopolitical = new Geopolitical(); GeopoliticalType geopoliticalType
		 * = new GeopoliticalType();
		 * geopoliticalType.setGeopoliticalTypeName("560037");
		 * geopolitical.setGeopoliticalType(geopoliticalType);
		 * geopoliticals.add(geopolitical);
		 * country.setGeopoliticals(geopoliticals);
		 */

		return Optional.of(country);
	}

	private Optional<List<AddressLabel>> getAddressLabel(String id) {
		List<AddressLabel> list = new ArrayList<>();
		AddressLabel addressLabel = new AddressLabel();

		addressLabel.setApplicableFlag("Y");
		addressLabel.setBrandAddressLineLabelDescription("City");
		addressLabel.setFullAddressLineLabelDescription("Street");
		addressLabel.setExpirationDate(Date.valueOf("9999-12-31"));
		addressLabel.setBrandAddressLineLabelDescription("fdfgdfd");
		addressLabel.setFullAddressLineLabelDescription("fdfgdfd");
		addressLabel.setApplicableFlag("a");
		addressLabel.setExpirationDate(Date.valueOf("9999-12-31"));
		addressLabel.setCreatedByUserId("1");
		addressLabel.setCreatedTmstp(CommonUtilityTest.getCurrentTimeStamp());
		addressLabel.setLastUpdatedByNm("B");
		addressLabel.setLastUpdatedTmstp(CommonUtilityTest.getCurrentTimeStamp());

		addressLabel.setId(new AddressLabelPK(id, Integer.valueOf(1), CommonUtilityTest.getCurrenctDate()));
		list.add(addressLabel);
		return Optional.of(list);
	}

	private Optional<List<CntryOrgStd>> getAllCntryOrgStds() {
		List<CntryOrgStd> list = new ArrayList<>();
		CntryOrgStd cntryOrgStd = new CntryOrgStd();
		GeoplOrgStd geoplOrgStd = new GeoplOrgStd();
		geoplOrgStd.setOrgStdNm("USA-Name");
		cntryOrgStd.setGeoplOrgStd(geoplOrgStd);
		cntryOrgStd.setCntryFullNm("USA");
		cntryOrgStd.setCntryShtNm("USA");
		cntryOrgStd.setExpirationDate(Date.valueOf("9999-12-31"));
		cntryOrgStd.setId(new CntryOrgStdPK("1234", "ISO", Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		list.add(cntryOrgStd);
		return Optional.of(list);
	}

	private Optional<List<Currency>> getByAllCurrencies() {

		List<Currency> list = new ArrayList<>();
		Currency currency = new Currency();
		currency.setCurrencyCd("USD");
		currency.setMinorUnitCd(BigInteger.valueOf(11l));
		currency.setMoneyFormatDescription("USD");
		currency.setExpirationDate(Date.valueOf("9999-12-31"));
		currency.setId(
				new CurrencyPK("1234", BigInteger.valueOf(11l), Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		list.add(currency);
		return Optional.of(list);
	}

	private Optional<List<AddressLabel>> getAddressLabelList() {
		List<AddressLabel> addressLabelList = new ArrayList<>();
		AddressLabel addressLabel = new AddressLabel();

		addressLabel.setApplicableFlag("Y");
		addressLabel.setBrandAddressLineLabelDescription("City");
		addressLabel.setFullAddressLineLabelDescription("Street");
		addressLabel.setExpirationDate(Date.valueOf("9999-12-31"));
		addressLabel.setBrandAddressLineLabelDescription("fdfgdfd");
		addressLabel.setFullAddressLineLabelDescription("fdfgdfd");
		addressLabel.setApplicableFlag("a");
		addressLabel.setExpirationDate(Date.valueOf("9999-12-31"));
		addressLabel.setCreatedByUserId("1");
		addressLabel.setCreatedTmstp(CommonUtilityTest.getCurrentTimeStamp());
		addressLabel.setLastUpdatedByNm("B");
		addressLabel.setLastUpdatedTmstp(CommonUtilityTest.getCurrentTimeStamp());

		addressLabel.setId(new AddressLabelPK("1234", Integer.valueOf(1), CommonUtilityTest.getCurrenctDate()));
		addressLabelList.add(addressLabel);

		return Optional.of(addressLabelList);
	}
}
