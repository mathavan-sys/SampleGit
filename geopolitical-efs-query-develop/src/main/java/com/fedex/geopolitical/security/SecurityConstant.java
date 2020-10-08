package com.fedex.geopolitical.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class SecurityConstant {

	@Value("${geopolitical.security.action}")
	private String action;

	@Value("${geopolitical.security.command.role}")
	public String geopoliticalCommand;

	@Value("${geopolitical.security.admin.role}")
	public String geopoliticalAdmin;

	@Value("${geopolitical.security.query.role}")
	public String geopoliticalQuery;

}
