package com.fedex.geopolitical.security;

import java.lang.reflect.Method;
import java.text.ParseException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fedex.framework.security.server.authentication.SecurityServiceException;
import com.fedex.framework.security.server.authentication.SecurityServicePrincipal;
import com.fedex.framework.security.server.authentication.SecurityServiceProperties;
import com.fedex.framework.security.server.authentication.TokenAuthenticator;
import com.fedex.geopolitical.api.v2.controller.TestConfig;

/**
 * @author 3755671
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TestConfig.class)
public class SecurityTest {

	@Mock
	ProviderManager authenticationManager;

	/** The remember me services. */
	@Mock
	RememberMeServices rememberMeServices;

	/** The authentication details source. */
	@Mock
	AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

	/** The token authenticator. */
	@Mock
	TokenAuthenticator tokenAuthenticator;

	@Mock
	HttpServletRequest request;

	//@Mock
	HttpServletResponse response = new MockHttpServletResponse();

	@Mock
	FilterChain chain;

	@Mock
	SecurityServicePrincipal principl;

	@Mock
	Authentication auth;

	/**
	 * @throws ParseException
	 * @throws JsonProcessingException
	 * @throws DuplicateRecordException
	 * @throws NoDataFoundException
	 */
	/*
	 * @Before public void setup() throws ParseException,
	 * JsonProcessingException, DuplicateRecordException, NoDataFoundException {
	 * 
	 * }
	 */

	@Test(expected = SecurityServiceException.class)
	public void testCachedCipherDaoImpl() throws SecurityServiceException {
		SecurityServiceProperties props = new SecurityServiceProperties();
		props.setLdapUrl("ldap://dirdev.corp.fedex.com:389/ou=people,o=fedex,c=us");
		props.setCipherAlgorithm("RSA");
		props.setLdapAppIdAttribute("uid");
		props.setLdapCertIdAttribute("userCertificate");
		props.setLdapConnTimeoutMillis("20000");
		props.setLdapContextFactory("com.sun.jndi.ldap.LdapCtxFactory");
		props.setLdapReadTimeoutMillis("20000");
		props.setLdapRevokeAttribute("FxAppSoxStatus");
		props.setTimeToLive(26000L);
		CachedCiphersDao cipherDaoImpl = Mockito.spy(new CachedCipherDaoImpl(props));
		Assert.assertNotNull(cipherDaoImpl.getCiphers("12345"));

	}

	@Test(expected = SecurityServiceException.class)
	public void testCachedCipherDaoImplNoCache() throws SecurityServiceException {
		SecurityServiceProperties props = new SecurityServiceProperties();
		props.setLdapUrl("ldap://dirdev.corp.fedex.com:389/ou=people,o=fedex,c=us");
		props.setCipherAlgorithm("RSA");
		props.setLdapAppIdAttribute("uid");
		props.setLdapCertIdAttribute("userCertificate");
		props.setLdapConnTimeoutMillis("20000");
		props.setLdapContextFactory("com.sun.jndi.ldap.LdapCtxFactory");
		props.setLdapReadTimeoutMillis("20000");
		props.setLdapRevokeAttribute("FxAppSoxStatus");
		props.setTimeToLive(26000L);
		CachedCiphersDao cipherDaoImpl = Mockito.spy(new CachedCipherDaoImpl(props));
		Assert.assertNotNull(cipherDaoImpl.getCiphersNoCache("123455"));

	}

	@Test
	public void testAuthenticator() throws Exception {
		FedExTokenAuthenticationFilter authenticationFilter = new FedExTokenAuthenticationFilter();
		authenticationFilter.setAuthenticationDetailsSource(authenticationDetailsSource);
		authenticationFilter.setAuthenticationManager(authenticationManager);
		authenticationFilter.setRememberMeServices(rememberMeServices);
		authenticationFilter.setServiceName("testService");
		authenticationFilter.setTokenAuthenticator(tokenAuthenticator);
		authenticationFilter.afterPropertiesSet();

		Class<?> filter = Class.forName("com.fedex.geopolitical.security.FedExTokenAuthenticationFilter");
		Method[] declaredMethods = filter.getDeclaredMethods();
		for (Method method : declaredMethods) {
			if (method.getName().equalsIgnoreCase("doFilterInternal")) {
				authenticationFilter = (FedExTokenAuthenticationFilter) filter.newInstance();
				method.setAccessible(true);
				authenticationFilter.setAuthenticationDetailsSource(authenticationDetailsSource);
				authenticationFilter.setAuthenticationManager(authenticationManager);
				authenticationFilter.setRememberMeServices(rememberMeServices);
				authenticationFilter.setServiceName("testService");
				authenticationFilter.setTokenAuthenticator(tokenAuthenticator);
				method.invoke(authenticationFilter, request, response, chain);
				Mockito.when(request.getHeader(ArgumentMatchers.anyString())).thenReturn("1234567890testService");
				Mockito.when(tokenAuthenticator.authenticateToken(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
						.thenReturn(principl);
				Mockito.when(principl.getClientId()).thenReturn("APP3534861");
				Mockito.when(authenticationManager.authenticate(ArgumentMatchers.any())).thenReturn(auth);
				method.invoke(authenticationFilter, request, response, chain);
				authenticationFilter.setAuthenticationManager(null);
				method.invoke(authenticationFilter, request, response, chain);
				Mockito.when(tokenAuthenticator.authenticateToken(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
						.thenThrow(new SecurityServiceException("Exception Testing"));
				method.invoke(authenticationFilter, request, response, chain);
				authenticationFilter.setServiceName("testServices");
				authenticationFilter.setTokenAuthenticator(tokenAuthenticator);
				method.invoke(authenticationFilter, request, response, chain);
			} else if (method.getName().equalsIgnoreCase("getRememberMeServices")) {
				method.setAccessible(true);
				method.invoke(authenticationFilter);
			} else if (method.getName().equalsIgnoreCase("getAuthenticationDetailsSource")) {
				method.setAccessible(true);
				method.invoke(authenticationFilter);
			} else if (method.getName().equalsIgnoreCase("getTokenAuthenticator")) {
				method.setAccessible(true);
				method.invoke(authenticationFilter);
			} else if (method.getName().equalsIgnoreCase("getAuthenticationManager")) {
				method.setAccessible(true);
				method.invoke(authenticationFilter);
			} else if (method.getName().equalsIgnoreCase("getServiceName")) {
				method.setAccessible(true);
				method.invoke(authenticationFilter);
			} else if (method.getName().equalsIgnoreCase("shouldNotFilter")) {
				method.setAccessible(true);
				Mockito.when(request.getServletPath()).thenReturn("/test/path");
				method.invoke(authenticationFilter, request);
			}
		}

	}

}
