/*package com.fedex.geopolitical.api.v2.controller;

import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.fedex.geopolitical.dtoresponse.RefUomTypeResponseDTO;
import com.fedex.geopolitical.dtoresponse.swaggerdtos.UOMResponseAll;
import com.fedex.geopolitical.model.RefUomType;
import com.fedex.geopolitical.service.SearchService;

@RestController
public class UomTypesController implements UomTypesApi, GeopoliticalApiV2 {
	private static final Logger LOGGER = LoggerFactory.getLogger(UomTypesController.class);
	@Autowired
	private SearchService<String, RefUomTypeResponseDTO, RefUomType> refUomTypeService;

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<UOMResponseAll> getReferenceUomTypes(String X_CSR_SECURITY_TOKEN, String contentType,
			String accept) {
		UOMResponseAll response = null;
		try {
			response = new UOMResponseAll(refUomTypeService.search(null));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<UOMResponseAll> getReferenceUomTypesOnUomTypeCd(String X_CSR_SECURITY_TOKEN,
			@Pattern(regexp = "^[a-zA-Z]{1,10}$") String uomTypeCd, String contentType, String accept) {
		UOMResponseAll response = null;

		try {
			response = new UOMResponseAll(refUomTypeService.search(uomTypeCd));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}*/