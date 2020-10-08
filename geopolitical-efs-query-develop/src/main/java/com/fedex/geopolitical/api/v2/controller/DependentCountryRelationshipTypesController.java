package com.fedex.geopolitical.api.v2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.fedex.geopolitical.dtoresponse.DependentCountryRelationshipTypeResponseDTO;
import com.fedex.geopolitical.dtoresponse.swaggerdtos.DependentCountryRelationshipTypesResponseAll;
import com.fedex.geopolitical.model.DependentCountryRelationship;
import com.fedex.geopolitical.service.SearchService;

@RestController
public class DependentCountryRelationshipTypesController
		implements DependentCountryRelationshipTypesApi, GeopoliticalApiV2 {
	private static final Logger LOGGER = LoggerFactory.getLogger(DependentCountryRelationshipTypesController.class);
	@Autowired
	private SearchService<String, DependentCountryRelationshipTypeResponseDTO, DependentCountryRelationship> dependentCountryRelationshipType;

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<DependentCountryRelationshipTypesResponseAll> getDependentCountryRelationshipTypes(String X_CSR_SECURITY_TOKEN,
			String contentType, String accept) {
		DependentCountryRelationshipTypesResponseAll response = null;
		try {
			response = new DependentCountryRelationshipTypesResponseAll( dependentCountryRelationshipType.search(null));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
