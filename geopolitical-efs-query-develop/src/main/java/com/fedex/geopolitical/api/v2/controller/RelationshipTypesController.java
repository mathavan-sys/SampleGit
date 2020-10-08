/*package com.fedex.geopolitical.api.v2.controller;

import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fedex.geopolitical.dtoresponse.GeopoliticalRelationshipTypeResponseDTO;
import com.fedex.geopolitical.dtoresponse.swaggerdtos.GeopoliticalRelationshipTypeResponseAll;
import com.fedex.geopolitical.model.GeopoliticalRelationshipType;
import com.fedex.geopolitical.service.SearchService;

@RestController
public class RelationshipTypesController implements RelationshipTypesApi, GeopoliticalApiV2 {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipTypesController.class);

	@Autowired
	private SearchService<String, GeopoliticalRelationshipTypeResponseDTO, GeopoliticalRelationshipType> geoPoliticalRelationshipTypesService;

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<GeopoliticalRelationshipTypeResponseAll> getRelationshipTypes(String X_CSR_SECURITY_TOKEN,
			String contentType, String accept) {
		GeopoliticalRelationshipTypeResponseAll response = null;
		try {
			response = new GeopoliticalRelationshipTypeResponseAll(geoPoliticalRelationshipTypesService.search(null));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<GeopoliticalRelationshipTypeResponseAll> getRelationshipTypesOnRtlspTyCd(
			String X_CSR_SECURITY_TOKEN,
			@Pattern(regexp = "^[a-zA-Z]{1,20}$") @PathVariable("relationshipTypeCode") String relationshipTypeCode,
			String contentType, String accept) {
		GeopoliticalRelationshipTypeResponseAll response = null;
		try {
			response = new GeopoliticalRelationshipTypeResponseAll(
					geoPoliticalRelationshipTypesService.search(relationshipTypeCode));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
*/