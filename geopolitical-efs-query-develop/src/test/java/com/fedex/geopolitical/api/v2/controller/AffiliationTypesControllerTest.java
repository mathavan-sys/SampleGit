package com.fedex.geopolitical.api.v2.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.fedex.geopolitical.model.GeoplAffilType;
import com.fedex.geopolitical.repository.GeoplAffilTypeRepository;
import com.fedex.geopolitical.utility.CommonUtilityTest;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@ActiveProfiles("test")
public class AffiliationTypesControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	GeoplAffilTypeRepository repository;
	
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

		GeoplAffilType affilType = new GeoplAffilType();
		affilType.setAffilTypeId(10l);
		affilType.setAffilTypeCode("ST");
		affilType.setCreatedByUserId("3766268");
		affilType.setCreatedTmstp(CommonUtilityTest.getCurrentTimeStamp());
		affilType.setLastUpdatedByUserId("3766268");
		affilType.setLastUpdatedTmstp(CommonUtilityTest.getCurrentTimeStamp());;
		
		repository.save(affilType);
	}

	@Test
	@WithUserDetails("APP3534861")

	public void createUpdateGeoplAffilType() throws Exception {
		
		//mockMvc.perform(MockMvcRequestBuilders.get(APIMappingConstants.V2+APIMappingConstants.GEOPL_AFFILIATION_TYPE).contentType(MediaType.APPLICATION_JSON)
			//	.accept(MediaType.APPLICATION_JSON).header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk()).andReturn();
		
		/*mockMvc.perform(MockMvcRequestBuilders.get(APIMappingConstants.V2+APIMappingConstants.GEOPL_AFFILIATION_TYPE_CD,"ST").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk()).andReturn();
		
		mockMvc.perform(MockMvcRequestBuilders.get(APIMappingConstants.V2+APIMappingConstants.GEOPL_AFFILIATION_TYPE_CD,"ED").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk()).andReturn();
		*/		
	}

}
