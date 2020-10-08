package com.fedex.geopolitical.security;

import java.util.List;

import javax.crypto.Cipher;

import com.fedex.framework.security.server.authentication.SecurityServiceException;
import com.fedex.framework.security.server.authentication.dao.CiphersDao;

// This new interface is needed due to Spring Proxy is based on interfaces, not classes
// Could change to CGLIB and specify to do it by classes.
/**
 * The Interface CachedCiphersDao.
 */
// https://stackoverflow.com/questions/14509142/beannotofrequiredtypeexception
public interface CachedCiphersDao extends CiphersDao {

	/**
	 * Gets the ciphers no cache.
	 *
	 * @param clientId the client id
	 * @return the ciphers no cache
	 * @throws SecurityServiceException the security service exception
	 */
	public List<Cipher> getCiphersNoCache(String clientId) throws SecurityServiceException;
}
