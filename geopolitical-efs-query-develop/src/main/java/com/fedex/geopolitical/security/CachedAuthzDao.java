package com.fedex.geopolitical.security;

import com.fedex.framework.security.server.authorization.AuthzSecurityException;
import com.fedex.framework.security.server.authorization.dao.DaoException;
import com.fedex.framework.security.server.authorization.domain.AuthzData;
import com.fedex.framework.security.server.authorization.model.AuthzDao;

/**
 * The Interface CachedAuthzDao.
 */
public interface CachedAuthzDao extends AuthzDao {

	/**
	 * Put authz data.
	 *
	 * @return the authz data
	 * @throws DaoException
	 *             the dao exception
	 * @throws AuthzSecurityException
	 *             the authz security exception
	 */
	AuthzData putAuthzData();
}
