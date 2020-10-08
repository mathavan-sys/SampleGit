package com.fedex.geopolitical.security;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fedex.framework.security.server.authentication.SecurityServiceException;
import com.fedex.framework.security.server.authentication.SecurityServicePrincipal;
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
 * The Class FedExTokenAuthenticationFilter. 
 */
/**
 * @author 3755319
 *
 */
public class FedExTokenAuthenticationFilter extends OncePerRequestFilter {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FedExTokenAuthenticationFilter.class);

	/** The authentication manager. */
	private ProviderManager authenticationManager;

	/** The remember me services. */
	private RememberMeServices rememberMeServices = new NullRememberMeServices();

	/** The authentication details source. */
	private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource = new WebAuthenticationDetailsSource();

	/** The token authenticator. */
	@Autowired
	private TokenAuthenticator tokenAuthenticator;

	/** The service name. */
	private String serviceName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.filter.GenericFilterBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() {
		Assert.notNull(this.tokenAuthenticator, "A TokenAuthenticator is required");
		Assert.notNull(this.serviceName, "A service name is required");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain chain) throws ServletException, IOException {

		String header = request.getHeader("X-CSR-SECURITY_TOKEN");
		MDC.put(GenericConstants.TRANSACTIONID, generateTransactionId());

		if (header == null || header.isEmpty()) {
			generateErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Missing HTTP header X-CSR-SECURITY_TOKEN");
		} else {
			try {
				header = URLDecoder.decode(header, "UTF8");
				final SecurityServicePrincipal principle = tokenAuthenticator.authenticateToken(header, serviceName);
				final PreAuthenticatedAuthenticationToken authRequest = new PreAuthenticatedAuthenticationToken(
						principle, header);
				authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
				// You can add a authenticator here for additional stuff
				if (authenticationManager == null) {
					this.rememberMeServices.loginSuccess(request, response, authRequest);
					SecurityContextHolder.getContext().setAuthentication(authRequest);
				} else {
					final Authentication authResp = authenticationManager.authenticate(authRequest); // throws
																										// AuthenticationException
																										// on
																										// failure
					this.rememberMeServices.loginSuccess(request, response, authResp);
					SecurityContextHolder.getContext().setAuthentication(authResp);
				}
				// Do any audit logging for authentication passed.
				LOGGER.info("Login passed");
				chain.doFilter(request, response);
			} catch (AuthenticationException | SecurityServiceException e) {
				// Do any audit logging for authentication failed. Probably do
				// NOT want logging like this unless it is in a different log
				// file.
				LOGGER.info("Login failed", e);
				this.rememberMeServices.loginFail(request, response);
				generateErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
			} catch (IllegalArgumentException iiae) {
				LOGGER.info("Login failed, illegal argument", iiae);
				this.rememberMeServices.loginFail(request, response);
				generateErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, iiae.getMessage());
			} finally {
				MDC.remove(GenericConstants.TRANSACTIONID);
			}
		}
	}

	private void generateErrorResponse(HttpServletResponse response, int status,  String message) throws IOException {
		response.setStatus(status);
		response.setContentType(GenericConstants.APPLICATION_JSON);
		response.getWriter().write(MessageHelper.asJsonString(generateErrorObject(status, message)));
		
	}

	public ApiError generateErrorObject(int status, String message) {
		MetaDTO metaDTO = new MetaDTO();
		MessageDTO messageDTO = new MessageDTO();
		DataDTO dataDTO = new DataDTO();
		dataDTO.setErrorMessage("Exception occurred in Security Filter");
		messageDTO.setStatus(ResponseStatus.ERROR);
		messageDTO.setData(dataDTO);
		metaDTO.setVersion(GenericConstants.VERSION);
		metaDTO.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaDTO.setStatusCode(String.valueOf(status));
		metaDTO.setMessage(messageDTO);
		List<ErrorDTO> errorDTOList = new ArrayList<>();
		ErrorDTO errorDTO = new ErrorDTO();
		messageDTO.setInternalMessage("Security Error");
		errorDTO.setFieldName("NA");
		errorDTO.setMessage(message);
		errorDTOList.add(errorDTO);
		return new ApiError(metaDTO, errorDTOList);
	}
	
	private String generateTransactionId() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * Gets the remember me services.
	 *
	 * @return the remember me services
	 */
	public RememberMeServices getRememberMeServices() {
		return rememberMeServices;
	}

	/**
	 * Sets the remember me services.
	 *
	 * @param rememberMeServices
	 *            the new remember me services
	 */
	public void setRememberMeServices(final RememberMeServices rememberMeServices) {
		Assert.notNull(rememberMeServices, "rememberMeServices cannot be null");
		this.rememberMeServices = rememberMeServices;
	}

	public AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> getAuthenticationDetailsSource() {
		return this.authenticationDetailsSource;
	}

	/**
	 * Sets the authentication details source.
	 *
	 * @param authenticationDetailsSource
	 *            the authentication details source
	 */
	public void setAuthenticationDetailsSource(
			final AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource) {
		Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.filter.OncePerRequestFilter#shouldNotFilter(javax
	 * .servlet.http.HttpServletRequest)
	 */
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return new AntPathMatcher().match("/actuator/**", request.getServletPath());

	}

	/**
	 * Gets the token authenticator.
	 *
	 * @return the token authenticator
	 */
	public TokenAuthenticator getTokenAuthenticator() {
		return tokenAuthenticator;
	}

	/**
	 * Sets the token authenticator.
	 *
	 * @param tokenAuthenticator
	 *            the new token authenticator
	 */
	public void setTokenAuthenticator(final TokenAuthenticator tokenAuthenticator) {
		this.tokenAuthenticator = tokenAuthenticator;
	}

	/**
	 * Gets the service name.
	 *
	 * @return the service name
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Sets the service name.
	 *
	 * @param serviceName
	 *            the new service name
	 */
	public void setServiceName(final String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * Gets the authentication manager.
	 *
	 * @return the authentication manager
	 */
	public ProviderManager getAuthenticationManager() {
		return authenticationManager;
	}

	/**
	 * Sets the authentication manager.
	 *
	 * @param authenticationManager
	 *            the new authentication manager
	 */
	public void setAuthenticationManager(final ProviderManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
}
