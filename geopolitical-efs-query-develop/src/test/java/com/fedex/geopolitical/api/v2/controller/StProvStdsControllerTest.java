package com.fedex.geopolitical.api.v2.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.fedex.geopolitical.constants.APIMappingConstants;
import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.model.Country;
import com.fedex.geopolitical.model.StProvStd;
import com.fedex.geopolitical.model.StProvStdPK;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@ActiveProfiles("test")
public class StProvStdsControllerTest {

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

		StProvStd stProvStd = new StProvStd();
		stProvStd.setStProvCd("CCCC");
		stProvStd.setStProvNm("CCCCCCC");
		stProvStd.setExpirationDate(Date.valueOf("2020-06-30"));
		StProvStdPK StProvStdPK = new StProvStdPK();
		StProvStdPK.setGeopoliticalId("1");
		StProvStdPK.setOrgStdCd("ISO");
		StProvStdPK.setEffectiveDate(Date.valueOf("2020-06-12"));
		Country country = new Country();
		country.setCountryCd("US");
	}

	@Test
	@WithUserDetails("APP3534861")

	public void getStProvStd() throws Exception {
		/*mockMvc.perform(MockMvcRequestBuilders
				.get(APIMappingConstants.V2 + APIMappingConstants.ST_PROV_STD_GEOPOLITICALID, "1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk())
				.andReturn();

		mockMvc.perform(MockMvcRequestBuilders
				.get(APIMappingConstants.V2 + APIMappingConstants.ST_PROV_STD
						+ "/1?targetDate=2020-06-11&endDate=2020-07-01")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk())
				.andReturn();

		mockMvc.perform(MockMvcRequestBuilders
				.get(APIMappingConstants.V2 + APIMappingConstants.ST_PROV_STD + "?stProvCd=CCCC&orgStdCd=ISO")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk())
				.andReturn();

		mockMvc.perform(
				MockMvcRequestBuilders.get(APIMappingConstants.V2 + APIMappingConstants.ST_PROV_STD + "?countryCd=US")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk()).andReturn();

		mockMvc.perform(MockMvcRequestBuilders
				.get(APIMappingConstants.V2 + APIMappingConstants.ST_PROV_STD
						+ "?countryCd=US&targetDate=2020-06-11&endDate=2020-07-01")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk())
				.andReturn();

		mockMvc.perform(MockMvcRequestBuilders
				.get(APIMappingConstants.V2 + APIMappingConstants.ST_PROV_STD
						+ "?stProvCd=bbb&orgStdCd=ISO&targetDate=2019-06-12&endDate=2022-12-09")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk())
				.andReturn();

		mockMvc.perform(MockMvcRequestBuilders
				.get(APIMappingConstants.V2 + APIMappingConstants.ST_PROV_STD
						+ "?stProvCd=bbb&orgStdCd=ISO&targetDate=2019-06-12")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk())
				.andReturn();

		mockMvc.perform(MockMvcRequestBuilders
				.get(APIMappingConstants.V2 + APIMappingConstants.ST_PROV_STD
						+ "?stProvCd=bbb&orgStdCd=ISO&endDate=2022-12-09")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk())
				.andReturn(); */
	}

}
