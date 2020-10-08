package com.fedex.geopolitical.api.v2.controller;

import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.fedex.geopolitical.dtoresponse.GeoplOrgStdResponseDTO;
import com.fedex.geopolitical.dtoresponse.swaggerdtos.GeopoliticalOrganizationStandardResponseAll;
import com.fedex.geopolitical.model.GeoplOrgStd;
import com.fedex.geopolitical.service.SearchService;

@RestController
public class GeopoliticalOrganizationStandardsController
		implements GeopoliticalOrganizationStandardsApi, GeopoliticalApiV2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeopoliticalOrganizationStandardsController.class);
	@Autowired
	private SearchService<String, GeoplOrgStdResponseDTO, GeoplOrgStd> geopoliticalOrgStdService;

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<GeopoliticalOrganizationStandardResponseAll> getOrganizationStandards(String X_CSR_SECURITY_TOKEN,
			String contentType, String accept) {
		GeopoliticalOrganizationStandardResponseAll response = null;
		try {
			response = new GeopoliticalOrganizationStandardResponseAll(geopoliticalOrgStdService.search(null));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<GeopoliticalOrganizationStandardResponseAll> getOrganizationStandardsOnCode(String X_CSR_SECURITY_TOKEN,
			@Pattern(regexp = "^[a-zA-Z]{1,10}$") String orgStandardCode, String contentType, String accept) {
		GeopoliticalOrganizationStandardResponseAll response = null;
		try {
			response = new GeopoliticalOrganizationStandardResponseAll( geopoliticalOrgStdService.search(orgStandardCode));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
