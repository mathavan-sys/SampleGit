package com.fedex.geopolitical.security;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fedex.framework.security.server.authorization.Authorizor;
import com.fedex.framework.security.server.authorization.AuthzSecurityException;
import com.fedex.framework.security.server.authorization.SecurityServiceAuthzProperties;
import com.fedex.framework.security.server.authorization.dao.DaoException;
import com.fedex.geopolitical.exceptionhandling.CustomControllerAdvise;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AuthorizationTest {

	@Test(expected = UsernameNotFoundException.class)
	public void testFedexUserDetails() throws DaoException, AuthzSecurityException {
		Authorizor authorizor = Mockito.mock(Authorizor.class);
		FedexUserDetailsService fedexUserDetailsService = new FedexUserDetailsService();
		fedexUserDetailsService.setAuthorizor(authorizor);
		ArrayList<String> roles = new ArrayList<>();
		roles.add("Admin");
		Mockito.when(authorizor.getUserRoles(ArgumentMatchers.anyString())).thenReturn(roles);
		UserDetails userDetails = fedexUserDetailsService.loadUserByUsername("SampleUser");
		FedexUserDetails fedexUserDetails = (FedexUserDetails) userDetails;
		Assert.assertNotNull(fedexUserDetails);
		fedexUserDetails.getAuthorities();
		fedexUserDetails.getPassword();
		fedexUserDetails.getUsername();
		fedexUserDetails.isAccountNonExpired();
		fedexUserDetails.isAccountNonLocked();
		fedexUserDetails.isCredentialsNonExpired();
		fedexUserDetails.isEnabled();
		fedexUserDetails.toString();
		Assert.assertNotNull(fedexUserDetailsService.loadUserByUsername("SampleUser"));
		Mockito.when(authorizor.getUserRoles(ArgumentMatchers.anyString()))
				.thenThrow(new AuthzSecurityException("AuthorizationException"));
		fedexUserDetailsService.loadUserByUsername("SampleUser");

	}

	@Test
	public void testAccessDeniedException() {
		CustomControllerAdvise exceptionHandler = new CustomControllerAdvise();
		Assert.assertEquals(403, exceptionHandler
				.handleAccessDeniedException(new AccessDeniedException("Access Denied")).getStatusCodeValue());
	}

	@Test
	public void testUserNameNotFoundException() {
		CustomControllerAdvise exceptionHandler = new CustomControllerAdvise();
		Assert.assertEquals(401,
				exceptionHandler.handleUsernameNotFoundException(new UsernameNotFoundException("User name not found"))
						.getStatusCodeValue());
	}

	@Test
	public void testCachedGrsGroupDaoImpl() throws DaoException {
		SecurityServiceAuthzProperties props = Mockito.mock(SecurityServiceAuthzProperties.class);
		CachedGrsGroupDaoImpl cachedGrsGroupDaoImpl = Mockito.spy(new CachedGrsGroupDaoImpl(props));
		Mockito.when(((CachedGrsGroupDao) cachedGrsGroupDaoImpl).putGroup("ABCD")).thenReturn(null);
		Assert.assertNull(cachedGrsGroupDaoImpl.putGroup("ABCD"));
	}

}
