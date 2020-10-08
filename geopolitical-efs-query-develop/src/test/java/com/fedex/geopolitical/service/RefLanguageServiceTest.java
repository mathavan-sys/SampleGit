package com.fedex.geopolitical.service;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedex.geopolitical.dto.LanguageDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.model.Country;
import com.fedex.geopolitical.model.CountryPK;
import com.fedex.geopolitical.model.Locale;
import com.fedex.geopolitical.model.LocalePK;
import com.fedex.geopolitical.model.RefLanguage;
import com.fedex.geopolitical.model.RefScript;
import com.fedex.geopolitical.model.TrnslDow;
import com.fedex.geopolitical.model.TrnslDowPK;
import com.fedex.geopolitical.model.TrnslMthOfYr;
import com.fedex.geopolitical.model.TrnslMthOfYrPK;
import com.fedex.geopolitical.repository.CountryRepository;
import com.fedex.geopolitical.repository.LocaleRepository;
import com.fedex.geopolitical.repository.RefLanguageRepository;
import com.fedex.geopolitical.repository.TrnslDowRepository;
import com.fedex.geopolitical.repository.TrnslMthOfYrRepository;
import com.fedex.geopolitical.service.impl.RefLanguageServiceImpl;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.CommonUtilityTest;
import com.fedex.geopolitical.utility.ResponseStatus;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class RefLanguageServiceTest {

	@Autowired
	ObjectMapper objectMapper;

	@Mock
	RefLanguageRepository refLanguageRepository;

	@Mock
	LocaleRepository localeRepository;

	@Mock
	CountryRepository countryRepository;

	@Mock
	TrnslDowRepository trnslDowRepository;

	@Mock
	TrnslMthOfYrRepository trnslMthOfYrRepository;

	@InjectMocks
	RefLanguageServiceImpl refLanguageService;

	@Test
	public void testGetLanguagesByLangCd() throws ParseException {

		List<RefLanguage> refLanguage = new ArrayList<>();
		Optional<RefLanguage> languageFirst = getRefLanguage("GE");
		Optional<RefLanguage> languageSecond = getRefLanguage("ENG");
		refLanguage.add(languageFirst.get());
		refLanguage.add(languageSecond.get());

		Mockito.when(refLanguageRepository.findByLangCd(Mockito.any())).thenReturn(languageFirst);

		List<Country> countries = new ArrayList<>();
		Country country = new Country();
		country.setCountryCd("cd");
		countries.add(country);
		Mockito.when(countryRepository.findByGeoplId(Mockito.any())).thenReturn(countries);

		Mockito.when(trnslDowRepository.findByLoclcode(Mockito.any())).thenReturn(getDows());

		Mockito.when(trnslMthOfYrRepository.findByLoclcode(Mockito.any())).thenReturn(getMoys());

		LanguageDTO languageDTO = new LanguageDTO();
		languageDTO.setLangCd("GE");
		QueryServiceResponseDTO queryServiceResponse = refLanguageService.search(languageDTO);

		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus().toString(), "SUCCESS");
	}

	@Test
	public void testGetLanguagesByCountryCd() throws ParseException {

		List<RefLanguage> refLanguage = new ArrayList<>();
		Optional<RefLanguage> languageFirst = getRefLanguage("GE");
		Optional<RefLanguage> languageSecond = getRefLanguage("ENG");
		refLanguage.add(languageFirst.get());
		refLanguage.add(languageSecond.get());

		List<Country> countries = new ArrayList<>();
		Country country = new Country();
		CountryPK id = new CountryPK();
		id.setGeopoliticalId("23456");
		country.setId(id);
		country.setCountryCd("cd");
		countries.add(country);

		List<Locale> locales = new ArrayList<>();
		locales.add(getLocales("1"));

		Mockito.when(countryRepository.findByCountryCd(Mockito.any())).thenReturn(Optional.of(countries));

		Mockito.when(localeRepository.findByGeopoliticalId(Mockito.any())).thenReturn(Optional.of(locales));

		Mockito.when(refLanguageRepository.findByLangCd(Mockito.any())).thenReturn(languageFirst);

		LanguageDTO languageDTO = new LanguageDTO();
		languageDTO.setCountryCd("GE");
		QueryServiceResponseDTO queryServiceResponse = refLanguageService.search(languageDTO);

		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus().toString(), "SUCCESS");
	}

	@Test
	public void testGetLanguagesByLocaleCd() throws ParseException {

		List<RefLanguage> refLanguage = new ArrayList<>();
		Optional<RefLanguage> languageFirst = getRefLanguage("GE");
		Optional<RefLanguage> languageSecond = getRefLanguage("ENG");
		refLanguage.add(languageFirst.get());
		refLanguage.add(languageSecond.get());

		List<Locale> locales = new ArrayList<>();
		locales.add(getLocales("1"));

		List<Country> countries = new ArrayList<>();
		Country country = new Country();
		CountryPK id = new CountryPK();
		id.setGeopoliticalId("23456");
		country.setId(id);
		country.setCountryCd("cd");
		countries.add(country);

		Mockito.when(localeRepository.findByLoclcode(Mockito.any())).thenReturn(Optional.of(locales));

		Mockito.when(refLanguageRepository.findByLangCd(Mockito.any())).thenReturn(languageFirst);

		LanguageDTO languageDTO = new LanguageDTO();
		languageDTO.setLocaleCd("MAS-GE");
		QueryServiceResponseDTO queryServiceResponse = refLanguageService.search(languageDTO);

		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus().toString(), "SUCCESS");
	}

	@Test
	public void testFindAllLanguages() throws ParseException {

		List<RefLanguage> refLanguage = new ArrayList<>();
		Optional<RefLanguage> languageFirst = getRefLanguage("GE");
		Optional<RefLanguage> languageSecond = getRefLanguage("ENG");
		refLanguage.add(languageFirst.get());
		refLanguage.add(languageSecond.get());

		Mockito.when(refLanguageRepository.findAll()).thenReturn(refLanguage);

		List<Country> countries = new ArrayList<>();
		Country country = new Country();
		country.setCountryCd("cd");
		countries.add(country);
		Mockito.when(countryRepository.findByGeoplId(Mockito.any())).thenReturn(countries);

		Mockito.when(trnslDowRepository.findByLoclcode(Mockito.any())).thenReturn(getDows());

		Mockito.when(trnslMthOfYrRepository.findByLoclcode(Mockito.any())).thenReturn(getMoys());

		LanguageDTO languageDTO = new LanguageDTO();

		QueryServiceResponseDTO queryServiceResponse = refLanguageService.search(languageDTO);

		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus().toString(), "SUCCESS");
	}

	private List<TrnslMthOfYr> getMoys() {
		List<TrnslMthOfYr> mockResults = new ArrayList<>();
		TrnslMthOfYr r1 = new TrnslMthOfYr();
		r1.setTrnslMthOfYrNm("karthik");
		r1.setId(new TrnslMthOfYrPK(4, "april"));
		mockResults.add(r1);
		return mockResults;
	}

	private List<TrnslDow> getDows() {
		List<TrnslDow> mockResults = new ArrayList<>();
		TrnslDow r1 = new TrnslDow();
		r1.setTrnslDowNm("somwar");
		r1.setId(new TrnslDowPK(1, "monday"));
		mockResults.add(r1);
		return mockResults;
	}

	private Optional<RefLanguage> getRefLanguage(String laguageCd) {
		RefLanguage LanguageObj = new RefLanguage();

		LanguageObj.setLangCd(laguageCd);
		LanguageObj.setEngLanguageNm("German");
		LanguageObj.setNativeScriptLanguageNm("GER");
		List<Locale> locales = new ArrayList<>();
		locales.add(getLocales("1"));
		LanguageObj.setLocales(locales);
		return Optional.of(LanguageObj);
	}

	private Locale getLocales(String geopoliticalId) {
		Locale locale = new Locale();
		RefLanguage refLanguage = new RefLanguage();
		refLanguage.setEngLanguageNm("ENG");
		locale.setRefLanguage(refLanguage);

		locale.setCldrVersionNumber("1.1");
		locale.setCldrVersionDate(Date.valueOf("2020-06-13"));
		locale.setCreatedByUserId("A");
		locale.setLangCd("A");
		locale.setLastUpdatedByNm("XYZ");
		locale.setLastUpdatedTmstp(CommonUtilityTest.getCurrentTimeStamp());
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
		Set<TrnslDow> trnslDowsSet = new HashSet<>();
		TrnslDow trnslDowFirst = new TrnslDow();
		TrnslDow trnslDowSecond = new TrnslDow();
		TrnslDowPK pk = new TrnslDowPK();
		pk.setDowNbr(1);
		trnslDowFirst.setId(pk);
		trnslDowFirst.setTrnslDowNm("Sunday");
		trnslDowSecond.setId(pk);
		trnslDowSecond.setTrnslDowNm("Sunday");
		trnslDowsSet.add(trnslDowFirst);
		trnslDowsSet.add(trnslDowSecond);
		Set<TrnslMthOfYr> trnslMthOfYrsSet = new HashSet<>();
		TrnslMthOfYr TrnslMthOfYrFirst = new TrnslMthOfYr();
		TrnslMthOfYr TrnslMthOfYrSecond = new TrnslMthOfYr();
		TrnslMthOfYrPK yrPk = new TrnslMthOfYrPK();
		yrPk.setMthOfYrNbr(1);
		TrnslMthOfYrFirst.setId(yrPk);
		TrnslMthOfYrFirst.setTrnslMthOfYrNm("January");
		TrnslMthOfYrSecond.setId(yrPk);
		TrnslMthOfYrSecond.setTrnslMthOfYrNm("January");
		trnslMthOfYrsSet.add(TrnslMthOfYrFirst);
		trnslMthOfYrsSet.add(TrnslMthOfYrSecond);
		return locale;
	}

}
