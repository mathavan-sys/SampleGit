/*package com.fedex.geopolitical.api.v2.controller;

import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.fedex.geopolitical.dtoresponse.GeoplAffilTypeResponseDTO;
import com.fedex.geopolitical.dtoresponse.swaggerdtos.GeopoliticalAffiliationTypeResponseAll;
import com.fedex.geopolitical.model.GeoplAffilType;
import com.fedex.geopolitical.service.SearchService;

@RestController
public class AffiliationTypesController implements AffiliationTypesApi, GeopoliticalApiV2 {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AffiliationTypesController.class);

	@Autowired
	private SearchService<String, GeoplAffilTypeResponseDTO, GeoplAffilType> geopoliticalAffilTypeService;

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<GeopoliticalAffiliationTypeResponseAll> getGeoplAffilTypes(String X_CSR_SECURITY_TOKEN, String contentType,
			String accept) {
		GeopoliticalAffiliationTypeResponseAll response = null;
		try {
			response = new GeopoliticalAffiliationTypeResponseAll( geopoliticalAffilTypeService.search(null));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<GeopoliticalAffiliationTypeResponseAll> getGeoplAffilTypesOnAffilTypeCode(String X_CSR_SECURITY_TOKEN,
			@Pattern(regexp = "^[a-zA-Z]{1,10}$") String affilTypeCode, String contentType, String accept) {
		GeopoliticalAffiliationTypeResponseAll response = null;
		try {
			response = new GeopoliticalAffiliationTypeResponseAll(geopoliticalAffilTypeService.search(affilTypeCode));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
*/