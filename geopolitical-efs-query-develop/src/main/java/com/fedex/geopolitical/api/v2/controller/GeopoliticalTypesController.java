/*package com.fedex.geopolitical.api.v2.controller;

import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.fedex.geopolitical.dtoresponse.GeopoliticalTypeResponseDTO;
import com.fedex.geopolitical.dtoresponse.swaggerdtos.GeopoliticalTypeResponseAll;
import com.fedex.geopolitical.model.GeopoliticalType;
import com.fedex.geopolitical.service.SearchService;

@RestController
public class GeopoliticalTypesController implements GeopoliticalTypesApi, GeopoliticalApiV2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeopoliticalTypesController.class);
	@Autowired
	private SearchService<String, GeopoliticalTypeResponseDTO, GeopoliticalType> geopoliticalTypeService;

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<GeopoliticalTypeResponseAll> getGeopoliticalTypes(String X_CSR_SECURITY_TOKEN, String contentType,
			String accept) {
		GeopoliticalTypeResponseAll response = null;
		try {
			response = new GeopoliticalTypeResponseAll( geopoliticalTypeService.search(null));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<GeopoliticalTypeResponseAll> getGeopoliticalTypesOnName(String X_CSR_SECURITY_TOKEN,
			@Pattern(regexp = "^[a-zA-Z_ ]{1,50}$") String geopoliticalTypeName, String contentType, String accept) {
		GeopoliticalTypeResponseAll response = null;
		try {
			response = new GeopoliticalTypeResponseAll( geopoliticalTypeService.search(geopoliticalTypeName));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
*/