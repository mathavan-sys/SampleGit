package com.fedex.geopolitical.security;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

import com.fedex.framework.security.client.TokenGenerator;
import com.fedex.framework.security.server.authorization.AuthzSecurityException;
import com.fedex.framework.security.server.authorization.SecurityServiceAuthzProperties;
import com.fedex.framework.security.server.authorization.dao.DaoException;
import com.fedex.framework.security.server.authorization.dao.cds.CdsAuthzDao;
import com.fedex.framework.security.server.authorization.dao.cds.CdsClientRetrieve;
import com.fedex.framework.security.server.authorization.dao.cds.CdsSecurityProfile;
import com.fedex.framework.security.server.authorization.dao.cds.springws.SpringSecurityInterceptor;
import com.fedex.framework.security.server.authorization.domain.AuthzData;
import com.fedex.framework.security.server.authorization.domain.AuthzRole;
import com.fedex.framework.security.server.authorization.model.AuthzDao;
import com.fedex.framework.security.server.authorization.model.GroupDao;

/**
 * To enable caching, extend the DAOs and inject it. The Authorizor uses a DAO
 * for the source of the authz data and group data. These DAOs are created by a
 * factory. The factory will use the bean with the IDs
 * com.fedex.framework.security.server.authz.AuthzDao and
 * com.fedex.framework.security.server.authz.GroupDao. If that bean does not
 * exist, it will create the default DAO object.
 */
@Component("com.fedex.framework.security.server.authz.AuthzDao")
public class CachedAuthzDaoImpl implements CachedAuthzDao {
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CachedAuthzDaoImpl.class);

	/** The authz dao. */
	private AuthzDao authzDao;

	/** The token generator. */
	@Autowired
	private TokenGenerator tokenGenerator;

	/** The props. */
	@Autowired
	private SecurityServiceAuthzProperties props;

	/** The group dao. */
	@Autowired
	@Qualifier("groupDaoFactory")
	private GroupDao groupDao;

	/**
	 * Post construct.
	 */
	@PostConstruct
	public void postConstruct() {
		Assert.notNull(props.getCdsServiceName(), "Service name can  not be null");
		Assert.notNull(props.getCdsUrl(), "Service name can  not be null");

		final WebServiceTemplate template = new WebServiceTemplate();
		template.setDefaultUri(props.getCdsUrl());

		final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("com.fedex.framework.security.server.authorization.dao.cds.jaxws");
		template.setMarshaller(marshaller);
		template.setUnmarshaller(marshaller);

		final SpringSecurityInterceptor interceptor = new SpringSecurityInterceptor(tokenGenerator);
		interceptor.setServiceName(props.getCdsServiceName());
		template.setInterceptors(new ClientInterceptor[] { interceptor });

		final CdsClientRetrieve client = new CdsClientRetrieve(template);
		final CdsSecurityProfile profile = new CdsSecurityProfile(client);
		authzDao = new CdsAuthzDao(props, profile);

		((CdsAuthzDao) authzDao).postConstruct();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fedex.framework.security.server.authorization.model.AuthzDao#
	 * getAuthzData()
	 */
	@Override
	@Cacheable(value = "AuthzDao", sync = true)
	public AuthzData getAuthzData() {
		AuthzData authzData = null;
		try {
			authzData = authzDao.getAuthzData();
		} catch (DaoException | AuthzSecurityException e) {
			LOGGER.error("Error while getting Authorization data", e);
			throw new SecurityException("Error while getting Authorization data", e);
		}
		return authzData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fedex.framework.sample.security.server.authorization.cache.
	 * CachedAuthzDao#putAuthzData()
	 *
	 * This will only be called by the scheduler.
	 *
	 */
	@Override
	@CachePut(value = "AuthzDao", unless = "#result==null")
	public AuthzData putAuthzData() {
		AuthzData authzData = null;

		try {
			authzData = authzDao.getAuthzData();
			for (final AuthzRole authzRole : authzData.getAuthzRoles().values()) {
				for (final String group : authzRole.getGroups()) {
					((CachedGrsGroupDao) groupDao).putGroup(group);
				}
			}
		} catch (final Exception e) {
			LOGGER.error("Scheduled task is unable to get authorization data from CDS", e);
		}

		return authzData;
	}

}
