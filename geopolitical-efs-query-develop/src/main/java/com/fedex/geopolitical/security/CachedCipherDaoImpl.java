package com.fedex.geopolitical.security;

import java.util.List;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.fedex.framework.security.server.authentication.SecurityServiceException;
import com.fedex.framework.security.server.authentication.SecurityServiceProperties;
import com.fedex.framework.security.server.authentication.dao.LdapCertificateCiphersDao;


/**
 * The Class CachedCipherDaoImpl.
 */
public class CachedCipherDaoImpl extends LdapCertificateCiphersDao implements CachedCiphersDao {
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CachedCipherDaoImpl.class);

	/**
	 * Instantiates a new cached cipher dao impl.
	 *
	 * @param props the props
	 */
	@Autowired
	public CachedCipherDaoImpl(final SecurityServiceProperties props) {
		super(props);
	}

	/* (non-Javadoc)
	 * @see com.fedex.framework.security.server.authentication.dao.AbstractCertificateCiphersDao#getCiphers(java.lang.String)
	 */
	@Override
	@Cacheable(value="AuthN", key="#clientId", sync=true)
	public List<Cipher> getCiphers(final String clientId) throws SecurityServiceException {
		LOGGER.info("CachedCipherDaoImpl.getCiphers() processing client ID: " + clientId);
		return super.getCiphers(clientId);
	}

	/* (non-Javadoc)
	 * @see com.fedex.framework.samples.security.server.authentication.springfilter.CachedCiphersDao#getCiphersNoCache(java.lang.String)
	 */
	@Override
	public List<Cipher> getCiphersNoCache(final String clientId) throws SecurityServiceException {
		return super.getCiphers(clientId);
	}
}
