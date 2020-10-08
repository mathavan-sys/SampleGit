package com.fedex.geopolitical.api.v2.controller;

import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.fedex.geopolitical.dtoresponse.RefScriptResponseDTO;
import com.fedex.geopolitical.dtoresponse.swaggerdtos.ScriptResponseAll;
import com.fedex.geopolitical.model.RefScript;
import com.fedex.geopolitical.service.SearchService;

@RestController

public class ScriptsController implements ScriptsApi, GeopoliticalApiV2 {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ScriptsController.class);
	@Autowired
	private SearchService<String, RefScriptResponseDTO, RefScript> refScriptService;

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<ScriptResponseAll> getReferenceScripts(String X_CSR_SECURITY_TOKEN, String contentType,
			String accept) {
		ScriptResponseAll response = null;
		try {
			response = new ScriptResponseAll(refScriptService.search(null));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<ScriptResponseAll> getReferenceScriptsOnCd(String X_CSR_SECURITY_TOKEN,
			@Pattern(regexp = "^[a-zA-Z0-9]{1,18}$") String scrptCd, String contentType, String accept) {
		ScriptResponseAll response = null;
		try {
			response = new ScriptResponseAll(refScriptService.search(scrptCd));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
