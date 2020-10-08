package com.fedex.geopolitical.api.v1.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;
import java.sql.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fedex.framework.security.server.authorization.AuthorizorImpl;
import com.fedex.framework.security.server.authorization.AuthzSecurityException;
import com.fedex.geopolitical.api.v2.controller.TestConfig;
import com.fedex.geopolitical.constants.APIMappingConstants;
import com.fedex.geopolitical.constants.GenericConstants;
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
import com.fedex.geopolitical.model.GeoplHday;
import com.fedex.geopolitical.model.GeoplHdayPK;
import com.fedex.geopolitical.model.GeoplUom;
import com.fedex.geopolitical.model.GeoplUomPK;
import com.fedex.geopolitical.model.GeopoliticalType;
import com.fedex.geopolitical.model.Holiday;
import com.fedex.geopolitical.model.Locale;
import com.fedex.geopolitical.model.LocalePK;
import com.fedex.geopolitical.model.RefLanguage;
import com.fedex.geopolitical.model.TrnslGeopl;
import com.fedex.geopolitical.model.TrnslGeoplPK;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@ActiveProfiles("test")
public class CountriesControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@LocalServerPort
	private int port;

	@MockBean
	AuthorizorImpl authorizor;

	@Before
	public void setup() throws AuthzSecurityException {

		RestAssuredMockMvc.webAppContextSetup(this.wac);
		RestAssured.port = port;
		when(authorizor.isAllowed(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(true);
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		Country country = new Country();
		country.setId(new CountryPK("1", Date.valueOf("2020-06-12")));
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
		country.setExpirationDate(Date.valueOf("2020-06-30"));
		CountryDialing countryDialing = new CountryDialing();
		countryDialing.setIntialDialingCd("011");
		countryDialing.setLandPhMaxLthNbr(BigInteger.valueOf(9));
		countryDialing.setLandPhMinLthNbr(BigInteger.valueOf(9));
		countryDialing.setMoblPhMaxLthNbr(BigInteger.valueOf(9));
		countryDialing.setMoblPhMinLthNbr(BigInteger.valueOf(9));
		countryDialing.setExpirationDate(Date.valueOf("2020-06-30"));
		CountryDialingPK pk = new CountryDialingPK();
		pk.setIntialDialingPrefixCd("+91");
		pk.setEffectiveDate(Date.valueOf("2020-06-12"));

		Currency currency = new Currency();
		currency.setCurrencyCd("USD");
		currency.setMinorUnitCd(BigInteger.valueOf(11l));
		currency.setMoneyFormatDescription("USD");
		currency.setExpirationDate(Date.valueOf("2020-06-30"));
		CurrencyPK currencyPK = new CurrencyPK();
		currencyPK.setCurrencyNumberCd(BigInteger.valueOf(11l));
		currencyPK.setEffectiveDate(Date.valueOf("2020-06-12"));

		GeoplUom geoplUom = new GeoplUom();
		geoplUom.setExpirationDate(Date.valueOf("2020-06-30"));
		GeoplUomPK geoplUomPK = new GeoplUomPK();
		geoplUomPK.setUomTypeCd("WT");
		geoplUomPK.setEffectiveDate(Date.valueOf("2020-06-12"));

		GeoplHday geoplHday = new GeoplHday();
		geoplHday.setExpirationDate(Date.valueOf("2020-06-30"));
		GeoplHdayPK geoplHdayPK = new GeoplHdayPK();
		geoplHdayPK.setEffectiveDate(Date.valueOf("2020-06-12"));
		Holiday holiday = new Holiday();
		holiday.setHolidayName("Hallowen");

		GeoplAffil geoplAffil = new GeoplAffil();
		geoplAffil.setExpirationDate(Date.valueOf("2020-06-30"));
		GeoplAffilPK geoplAffilPK = new GeoplAffilPK();
		geoplAffilPK.setAffilTypeId(1l);
		geoplAffilPK.setEffectiveDate(Date.valueOf("2020-06-12"));

		RefLanguage refLanguage = new RefLanguage();
		refLanguage.setEngLanguageNm("ENG");

		Locale locale = new Locale();
		locale.setRefLanguage(refLanguage);
		locale.setCldrVersionNumber("1.1");
		locale.setCldrVersionDate(Date.valueOf("2020-06-13"));
		locale.setDateFullFormatDescription("XYZ");
		locale.setDateLongFormatDescription("XYZ");
		locale.setDateMediumFormatDescription("XYZ");
		locale.setDateShortFormatDescription("XYZ");
		locale.setExpirationDate(Date.valueOf("2020-06-30"));
		LocalePK localePK = new LocalePK();
		localePK.setLoclcode("EN");
		localePK.setEffectiveDate(Date.valueOf("2020-06-12"));

		TrnslGeopl trnslGeopl = new TrnslGeopl();
		trnslGeopl.setTranslationName("United States of America");
		trnslGeopl.setVersionNumber("1");
		trnslGeopl.setVersionDate(Date.valueOf("2020-06-13"));
		trnslGeopl.setExpirationDate(Date.valueOf("2020-06-30"));
		TrnslGeoplPK trnslGeoplPK = new TrnslGeoplPK();
		locale.setRefLanguage(refLanguage);
		trnslGeoplPK.setEffectiveDate(Date.valueOf("2020-06-12"));

		CntryOrgStd cntryOrgStd = new CntryOrgStd();
		cntryOrgStd.setCntryFullNm("USA");
		cntryOrgStd.setCntryShtNm("USA");
		cntryOrgStd.setExpirationDate(Date.valueOf("2020-06-30"));
		CntryOrgStdPK cntryOrgStdPK = new CntryOrgStdPK();
		cntryOrgStdPK.setOrgStdCd("ISO");
		cntryOrgStdPK.setEffectiveDate(Date.valueOf("2020-06-12"));

		GeopoliticalType geopoliticalType = new GeopoliticalType();
		geopoliticalType.setGeopoliticalTypeName("560037");

	}

	@Test
	@WithUserDetails("APP3534861")
	public void countryControllerTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(APIMappingConstants.COUNTRY_GEOPOLITICAL_ID, 1)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk())
				.andReturn();

		mockMvc.perform(MockMvcRequestBuilders.get(APIMappingConstants.COUNTRY + "?geopoliticalId=1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk())
				.andReturn();

		mockMvc.perform(MockMvcRequestBuilders.get(APIMappingConstants.COUNTRY + "?countryCd=US")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk())
				.andReturn();

		mockMvc.perform(MockMvcRequestBuilders
				.get(APIMappingConstants.COUNTRY + "?countryCd=US&targetDate=2020-06-13&endDate=2020-06-20")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk())
				.andReturn();

		mockMvc.perform(
				MockMvcRequestBuilders.get(APIMappingConstants.COUNTRY + "?countryShortName=USA&orgStandardCode=ISO")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk()).andReturn();

		mockMvc.perform(MockMvcRequestBuilders
				.get(APIMappingConstants.COUNTRY
						+ "?countryShortName=USA&orgStandardCode=ISO&targetDate=2020-06-13&endDate=2020-06-20")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk())
				.andReturn();

	}
}
