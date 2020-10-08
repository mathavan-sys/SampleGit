package com.fedex.geopolitical.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.channel.ChannelEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessor;
import org.springframework.security.web.access.channel.SecureChannelProcessor;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;

import com.fedex.framework.security.server.authentication.SecurityServiceProperties;
import com.fedex.framework.security.server.authentication.TokenAuthenticator;
import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dtometa.DataDTO;
import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;
import com.fedex.geopolitical.errorresponse.ApiError;
import com.fedex.geopolitical.errorresponse.ErrorDTO;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.MessageHelper;
import com.fedex.geopolitical.utility.ResponseStatus;

/**
 * The Class CustomWebSecurityConfigurerAdapter.
 */
@Configuration
@EnableCaching
@Order
@EnableWebSecurity
public class GeopoliticalEfsAuthenticationConfig extends WebSecurityConfigurerAdapter {
	
	
	
	/** The service name. */
	@Value("${service.name}")
	private String serviceName;

	/** The cache specs. */
	// These contain various config options for the caffeine cache
	@Value("${spring.cache.caffeine.specs}")
	private String cacheSpecs;

	/** The cache name. */
	@Value("${spring.cache.cache-name}")
	private String cacheName;

	@Autowired
	private FedexUserDetailsService fedexUserDetailsService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter#configure(org.springframework.security.
	 * config.annotation.web.builders.HttpSecurity)
	 */

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.requiresChannel().requestMatchers(r -> r.getHeader(GenericConstants.X_FORWARDED_PROTO) != null).requiresSecure()
				.channelProcessors(Arrays.asList(EFSSecureChannelProcessor())).and().csrf().disable()
				.addFilterAfter(authFilter(), X509AuthenticationFilter.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter#configure(org.springframework.security.
	 * config.annotation.authentication.builders.AuthenticationManagerBuilder)
	 */
	@Override
	protected void configure(final AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticator());
	}

	/**
	 * Auth filter.
	 *
	 * @return the fed ex token authentication filter
	 */
	@Bean
	public FedExTokenAuthenticationFilter authFilter() {
		final FedExTokenAuthenticationFilter filter = new FedExTokenAuthenticationFilter();
		filter.setServiceName(serviceName);
		return filter;
	}

	/**
	 * Authenticator.
	 *
	 * @return the authentication provider
	 */
	@Bean
	public AuthenticationProvider authenticator() {
		final PreAuthenticatedAuthenticationProvider paap = new PreAuthenticatedAuthenticationProvider();
		paap.setPreAuthenticatedUserDetailsService(userDetailsServiceWrapper());

		return paap;
	}

	/**
	 * User details service wrapper.
	 *
	 * @return the user details by name service wrapper
	 */
	@Bean
	UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> userDetailsServiceWrapper() {
		final UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = new UserDetailsByNameServiceWrapper<>();
		wrapper.setUserDetailsService(userDetailsService());

		return wrapper;
	}

	/**
	 * Cache manager.
	 *
	 * @return the caffeine cache manager
	 * 
	 * 
	 * 
	 * 
	 * 
	 *         Cipher dao.
	 *
	 * @return the cached ciphers dao
	 */
	// This *exact* bean name is required by the REDUX component
	@Bean(name = "com.fedex.framework.security.server.authn.CipherDao")
	public CachedCiphersDao cipherDao() {
		return new CachedCipherDaoImpl(securityServiceProperties());
	}

	/**
	 * Security service properties.
	 *
	 * @return the security service properties
	 */
	@Bean
	public SecurityServiceProperties securityServiceProperties() {
		return new SecurityServiceProperties();
	}

	/**
	 * Token authenticator.
	 *
	 * @return the token authenticator
	 */
	@Bean
	public TokenAuthenticator tokenAuthenticator() {
		return new TokenAuthenticator(cipherDao());
	}

	@Override
	public UserDetailsService userDetailsService() {
		return fedexUserDetailsService;
	}

	/**
	 * This is only for responding to invalid request in JSON format for
	 * forwarded header protocol and HTTP instead of an HTTPS request
	 *
	 */
	@Bean
	public ChannelProcessor EFSSecureChannelProcessor() {
		SecureChannelProcessor secureChannelProcessor = new SecureChannelProcessor();
		secureChannelProcessor.setEntryPoint(new SendHttpsError());
		return secureChannelProcessor;
	}

	public class SendHttpsError implements ChannelEntryPoint {
		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {			
			CommonUtility.populateForbidden_Status_StatusCd_TS(request,response);
		}
	}
}