/*package com.fedex.geopolitical.api.v2.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

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
import com.fedex.geopolitical.model.RefLanguage;
import com.fedex.geopolitical.model.RefScript;
import com.fedex.geopolitical.model.TrnslDow;
import com.fedex.geopolitical.model.TrnslDowPK;
import com.fedex.geopolitical.model.TrnslMthOfYr;
import com.fedex.geopolitical.model.TrnslMthOfYrPK;
import com.fedex.geopolitical.repository.RefLanguageRepository;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@ActiveProfiles("test")
public class LanguagesControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	RefLanguageRepository repository;

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

		RefLanguage LanguageObj = new RefLanguage();
		RefScript refScript = new RefScript();
		refScript.setScrptCd("ENG");
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
		TrnslMthOfYrFirst.setTrnslMthOfYrNm("January");
		TrnslMthOfYrSecond.setTrnslMthOfYrNm("January");
		trnslMthOfYrsSet.add(TrnslMthOfYrFirst);
		trnslMthOfYrsSet.add(TrnslMthOfYrSecond);
		LanguageObj.setLangCd("ENG");
		LanguageObj.setLanguageNm("German");
		LanguageObj.setNativeScriptLanguageNm("GER");
		repository.save(LanguageObj);

	}

	// TODO: Fix this test
	@Test
	@WithUserDetails("APP3534861")

	public void getLanguage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(APIMappingConstants.V2+APIMappingConstants.GEOPOLITICAL_LANGUAGES)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk()).andReturn();

		mockMvc.perform(MockMvcRequestBuilders.get(APIMappingConstants.V2+APIMappingConstants.GEOPOLITICAL_LANGUAGES_CD, "ENG")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).header(GenericConstants.SECURITY_TOKEN, MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk()).andReturn();
	}

}
*/