package com.fedex.geopolitical.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.fedex.framework.security.server.authorization.SecurityServiceAuthzProperties;
import com.fedex.framework.security.server.authorization.dao.DaoException;
import com.fedex.framework.security.server.authorization.dao.grs.GrsGroupDao;

/**
 * To enable caching, extend the DAOs and inject it. The Authorizor uses a DAO
 * for the source of the authz data and group data. These DAOs are created by a
 * factory. The factory will use the bean with the IDs
 * com.fedex.framework.security.server.authz.AuthzDao and
 * com.fedex.framework.security.server.authz.GroupDao. If that bean does not
 * exist, it will create the default DAO object.
 */
@Component("com.fedex.framework.security.server.authz.GroupDao")
public class CachedGrsGroupDaoImpl extends GrsGroupDao implements CachedGrsGroupDao {
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CachedGrsGroupDaoImpl.class);

	/**
	 * Instantiates a new cached grs group dao.
	 *
	 * @param props
	 *            the props
	 */
	@Autowired
	public CachedGrsGroupDaoImpl(final SecurityServiceAuthzProperties props) {
		super(props);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fedex.framework.security.server.authorization.dao.grs.GrsGroupDao#
	 * getGroup(java.lang.String)
	 */
	@Override
	@Cacheable(value = "GroupDao", sync = true)
	public List<String> getGroup(final String groupName) throws DaoException {
		return super.getGroup(groupName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fedex.framework.sample.security.server.authorization.cache.
	 * CachedGrsGroupDao#putGroup(java.lang.String)
	 */
	@Override
	@CachePut(value = "GroupDao", unless = "#result==null")
	public List<String> putGroup(final String groupName) {
		List<String> ret = null;
		try {
			ret = super.getGroup(groupName);
		} catch (final Exception e) {
			LOGGER.error("Scheduled task is unable to get group " + groupName + " from GRS", e);
		}
		return ret;
	}

}
