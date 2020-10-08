package com.fedex.geopolitical.api.v2.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.InitialContext;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.fedex.framework.security.client.FedExClientCredentials;
import com.fedex.geopolitical.security.FedexUserDetails;

@TestConfiguration
public class TestConfig {

	@MockBean
	InitialContext initialContext;

	@Bean
	@Primary
	UserDetailsService userDetailsService() {
		List<String> roles = new ArrayList<String>();
		roles.add("USER");
		FedexUserDetails details = new FedexUserDetails("APP3534861", roles);
		return new InMemoryUserDetailsManager(Arrays.asList(details));
	}

	@Bean
	com.fedex.framework.security.client.FedExClientCredentials fedExClientCredentials() {
		return Mockito.mock(FedExClientCredentials.class);
	}

	@Bean(name = "getJndiContext", value = "getJndiContext")
	@Primary
	InitialContext getJndiContext(String username, String password) {
		return Mockito.mock(InitialContext.class);
	}

}
