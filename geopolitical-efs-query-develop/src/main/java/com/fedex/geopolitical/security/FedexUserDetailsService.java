package com.fedex.geopolitical.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.fedex.framework.security.server.authorization.Authorizor;
import com.fedex.framework.security.server.authorization.AuthzSecurityException;
import com.fedex.framework.security.server.authorization.dao.DaoException;

import lombok.Data;

/**
 * The Class SampleUserDetailsService.
 */
@Component
@Data
public class FedexUserDetailsService implements UserDetailsService {

	/** The authorizor. */
	@Autowired
	private Authorizor authorizor;

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		try {
			return new FedexUserDetails( username, authorizor.getUserRoles(username) );
		} catch (DaoException | AuthzSecurityException e) {
			throw new UsernameNotFoundException("Unable to find user", e);
		}
	}

}
