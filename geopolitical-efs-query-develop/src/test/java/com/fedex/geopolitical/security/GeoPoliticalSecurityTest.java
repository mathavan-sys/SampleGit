package com.fedex.geopolitical.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;


import com.fedex.framework.security.server.authentication.SecurityServiceException;
import com.fedex.framework.security.server.authentication.SecurityServicePrincipal;
import com.fedex.framework.security.server.authentication.TokenAuthenticator;
import com.fedex.geopolitical.api.v2.controller.TestConfig;
import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
public class GeoPoliticalSecurityTest {

	private static final String url = "/holidays";

	private static final String token = "MyTokenMbLN6MQ%3D%3AAPP3534861";

	@MockBean
	private TokenAuthenticator tokenAuthenticator;

	@Autowired
	private FedExTokenAuthenticationFilter filter;

	@Before
	public void setUp() throws SecurityServiceException {

		SecurityServicePrincipal securityPrincipal = Mockito.mock(SecurityServicePrincipal.class);
		Mockito.when(securityPrincipal.getClientId()).thenReturn("APP3534861");
		when(tokenAuthenticator.authenticateToken(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(securityPrincipal);

	}

	@Test
	@WithUserDetails("APP3534861")
	public void securityTestWithOutHeader() throws ServletException, IOException, SecurityServiceException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI(url);
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain filterChain = new MockFilterChain();
		filter.doFilter(request, response, filterChain);
		assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
	}

	@Test
	@WithUserDetails("APP3534861")
	public void securityTestWithHeader() throws ServletException, IOException, SecurityServiceException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("X-CSR-SECURITY_TOKEN", token);
		request.setRequestURI(url);
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain filterChain = new MockFilterChain();

		filter.doFilter(request, response, filterChain);
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

	}

	@Test
	@WithUserDetails("APP3534861")
	public void securityTestWithHealthURL() throws ServletException, IOException, SecurityServiceException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("X-CSR-SECURITY_TOKEN", token);
		request.setRequestURI("/actuator/health");
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain filterChain = new MockFilterChain();

		filter.doFilter(request, response, filterChain);
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	public void populateSuccess_Status_StatusCd_TS() {
		MessageDTO message =new MessageDTO();
		MetaDTO metaResponse = new MetaDTO();
		CommonUtility.populateSuccess_Status_StatusCd_TS(message, metaResponse);
		assertTrue(message.getStatus().equals(ResponseStatus.SUCCESS));
	}
	@Test
	@WithUserDetails("APP3534861")
	public void populateForbidden_Status_StatusCd_TS() {
		try {
			MockHttpServletResponse response = new MockHttpServletResponse();
			MockHttpServletRequest request = new MockHttpServletRequest();
			request.addHeader("X-CSR-SECURITY_TOKEN", token);
			CommonUtility.populateForbidden_Status_StatusCd_TS(request, response);
		}catch(Exception e) {
			e.getMessage();
		}
	}


}
