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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fedex.framework.security.server.authorization.AuthorizorImpl;
import com.fedex.framework.security.server.authorization.AuthzSecurityException;
import com.fedex.geopolitical.constants.APIMappingConstants;
import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.model.AddressLabel;
import com.fedex.geopolitical.model.AddressLabelPK;
import com.fedex.geopolitical.repository.AddressLabelRepository;
import com.fedex.geopolitical.utility.CommonUtilityTest;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {TestConfig.class})
public class AddressLabelsControllerTest {
	private MockMvc mockMvc;

	@Autowired
	AddressLabelRepository repository;

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

		AddressLabel addressLabel = new AddressLabel();
		addressLabel.setCreatedByUserId("3766268");
		addressLabel.setCreatedTmstp(CommonUtilityTest.getCurrentTimeStamp());
		addressLabel.setLastUpdatedTmstp(CommonUtilityTest.getCurrentTimeStamp());
		;

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
		AddressLabelPK addressLabelPK = new AddressLabelPK();
		addressLabelPK.setGeopoliticalId("12901290192l");
		addressLabelPK.setAddressLineNumber(Integer.valueOf(1));
		addressLabelPK.setLoclcode("AR");
		addressLabel.setId(addressLabelPK);

		repository.save(addressLabel);
	}

	@Test
	@WithUserDetails("APP3534861")
	public void allAddresses() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get(APIMappingConstants.V2+APIMappingConstants.ADDRESS_LABEL)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk())
				.andReturn();
		mockMvc.perform(MockMvcRequestBuilders.get(APIMappingConstants.V2+APIMappingConstants.ADDRESS_LABEL+"?targetDate=2020-16-05&endDate=2020-16-02")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isBadRequest())
				.andReturn();

	}

	@Test
	@WithUserDetails("APP3534861")
	public void addressByGeopoliticalId() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get(APIMappingConstants.V2+APIMappingConstants.ADDRESS_LABEL+ "/12901290192")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk())
				.andReturn();
		mockMvc.perform(MockMvcRequestBuilders.get(APIMappingConstants.V2+APIMappingConstants.ADDRESS_LABEL+ "/12901290192?targetDate=2020-16-05&endDate=2020-16-02")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isBadRequest())
				.andReturn();

	}

	@Test
	@WithUserDetails("APP3534861")
	public void addressByGeopoliticalIdLocaleCode() throws Exception {

		mockMvc.perform(
				MockMvcRequestBuilders.get(APIMappingConstants.V2+APIMappingConstants.ADDRESS_LABEL + "/12901290192/AR")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk()).andReturn();
		mockMvc.perform(
				MockMvcRequestBuilders.get(APIMappingConstants.V2+APIMappingConstants.ADDRESS_LABEL + "/12901290192/AR?targetDate=2020-16-05&endDate=2020-16-02")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isBadRequest()).andReturn();

	}
	
	@Test
	@WithUserDetails("APP3534861")
	public void addressByGeopoliticalIdLocaleCodeLineNumber() throws Exception {

		mockMvc.perform(
				MockMvcRequestBuilders.get(APIMappingConstants.V2+APIMappingConstants.ADDRESS_LABEL + "/12901290192/AR/2")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk()).andReturn();
		mockMvc.perform(
				MockMvcRequestBuilders.get(APIMappingConstants.V2+APIMappingConstants.ADDRESS_LABEL + "/12901290192/AR/2?targetDate=2020-16-05&endDate=2020-16-02")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isBadRequest()).andReturn();

	}
	
}
