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
import com.fedex.geopolitical.model.GeoplRltsp;
import com.fedex.geopolitical.model.GeoplRltspPK;
import com.fedex.geopolitical.model.GeopoliticalRelationshipType;
import com.fedex.geopolitical.repository.GeoplRltspRepository;
import com.fedex.geopolitical.repository.GeopoliticalRelationshipTypeRepository;
import com.fedex.geopolitical.utility.CommonUtilityTest;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@ActiveProfiles("test")
public class RelationshipsControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private GeoplRltspRepository repository;

	@Autowired
	GeopoliticalRelationshipTypeRepository rltspRepository;

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

		GeopoliticalRelationshipType geopoliticalRelationshipType = new GeopoliticalRelationshipType();
		geopoliticalRelationshipType.setGeopoliticalRelationshipTypeCd("CS");
		geopoliticalRelationshipType.setAreaRelationshipTypeDescription("Country State");
		geopoliticalRelationshipType.setCreatedByUserId("3766271");
		geopoliticalRelationshipType.setLastUpdatedByNm("3766271");
		rltspRepository.save(geopoliticalRelationshipType);

		GeoplRltsp geopoliticalRelationship = new GeoplRltsp();
		geopoliticalRelationship.setId(new GeoplRltspPK("2", "3", "CS", Date.valueOf("2019-06-30")));
		geopoliticalRelationship.setExpirationDate(CommonUtilityTest.getDefaultExpirationDate());
		geopoliticalRelationship.setCreatedByUserId("3766268");
		geopoliticalRelationship.setCreatedTmstp(CommonUtilityTest.getCurrentTimeStamp());
		geopoliticalRelationship.setLastUpdatedByNm("3766268");
		geopoliticalRelationship.setLastUpdatedTmstp(CommonUtilityTest.getCurrentTimeStamp());
		repository.save(geopoliticalRelationship);

	}

	@Test
	@WithUserDetails("APP3534861")

	public void createUpdateGeopoliticalRelationshipType() throws Exception {

		/*mockMvc.perform(
				MockMvcRequestBuilders.get(APIMappingConstants.V2 + APIMappingConstants.GEOPOLITICAL_RELATIONSHIP)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk()).andReturn();

		mockMvc.perform(MockMvcRequestBuilders
				.get(APIMappingConstants.V2 + APIMappingConstants.GEOPOLITICAL_RELATIONSHIP
						+ "?relationshipTypeCode={relationshipTypeCode}", "CS")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk())
				.andReturn();

		mockMvc.perform(MockMvcRequestBuilders
				.get(APIMappingConstants.V2 + APIMappingConstants.GEOPOLITICAL_RELATIONSHIP
						+ "?relationshipTypeCode=CS&targetDate=2019-06-30&endDate=9999-12-31")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk())
				.andReturn();

		mockMvc.perform(MockMvcRequestBuilders
				.get(APIMappingConstants.V2 + APIMappingConstants.GEOPOLITICAL_RELATIONSHIP
						+ "?relationshipTypeCode=CS&targetDate=2019-06-30")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk())
				.andReturn();

		mockMvc.perform(MockMvcRequestBuilders
				.get(APIMappingConstants.V2 + APIMappingConstants.GEOPOLITICAL_RELATIONSHIP
						+ "?relationshipTypeCode=CS")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk())
				.andReturn();

		mockMvc.perform(MockMvcRequestBuilders
				.get(APIMappingConstants.V2 + APIMappingConstants.GEOPOLITICAL_RELATIONSHIP
						+ "?relationshipTypeCode=CS&fromGeopoliticalId=1234&toGeopoliticalId=234")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk())
				.andReturn();
		*/
	}

}