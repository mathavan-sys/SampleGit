package com.fedex.geopolitical.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * The Class SampleUserDetails.
 */
public class FedexUserDetails implements UserDetails {

	/** Serial Number. */
	private static final long serialVersionUID = 1L;

	/** The user name. */
	private final String userName;

	/** The granted authoritys. */
	private final Collection<? extends GrantedAuthority> grantedAuthoritys;

	/**
	 * Instantiates a new sample user details.
	 *
	 * This will load all the roles for the user. The user may be cached in the
	 * session. This means any changes to roles will not be picked up until the
	 * session is closed and a new session begins.
	 *
	 * @param userName
	 *            the user name
	 * @param roles
	 *            the roles
	 */
	public FedexUserDetails(final String userName, final List<String> roles) {
		this.userName = userName;

		final StringBuilder stringBuilder = new StringBuilder();
		final List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
		for (final String role : roles) {
			stringBuilder.setLength(0);
			stringBuilder.append("ROLE_").append(role);
			authorities.add(new SimpleGrantedAuthority(stringBuilder.toString()));
		}
		grantedAuthoritys = Collections.unmodifiableList(authorities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getAuthorities(
	 * )
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthoritys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	@Override
	public String getPassword() {
		return "{noop}" + userName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	public String getUsername() {
		return userName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#
	 * isAccountNonExpired()
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#
	 * isAccountNonLocked()
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#
	 * isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getUsername();
	}

}
