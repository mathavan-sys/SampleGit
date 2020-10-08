/*
package com.fedex.geopolitical.api.v2.controller;

import java.math.BigInteger;

import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.fedex.geopolitical.constants.DTOValidationConstants;
import com.fedex.geopolitical.dtoresponse.DependentCountryRelationshipResponseDTO;
import com.fedex.geopolitical.dtoresponse.swaggerdtos.DependentCountryRelationshipResponseAll;
import com.fedex.geopolitical.exception.BigIntegerFormatException;
import com.fedex.geopolitical.service.SearchService;

@RestController
public class DependentCountryRelationshipsController implements DependentCountryRelationshipsApi, GeopoliticalApiV2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(DependentCountryRelationshipsController.class);
	@Autowired
	private SearchService<BigInteger, DependentCountryRelationshipResponseDTO, DependentCountryRelationshipResponseDTO> dependentCountryRelationship;

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<DependentCountryRelationshipResponseAll> getDependentCountryRelationship(String X_CSR_SECURITY_TOKEN,
			@Pattern(regexp = "^[0-9]{1,65}$") String dependentCountryCdString, String contentType, String accept) {

		BigInteger dependentCountryCd = null;
		try {
			try {
				dependentCountryCd = new BigInteger(dependentCountryCdString);
			} catch (Exception e) {
				throw new BigIntegerFormatException(DTOValidationConstants.ID_WRONG_FORMAT);
			}

			DependentCountryRelationshipResponseAll response = new DependentCountryRelationshipResponseAll( dependentCountryRelationship.search(dependentCountryCd));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
*/