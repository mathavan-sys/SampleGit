package com.fedex.geopolitical.api.v2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.fedex.geopolitical.dtoresponse.RefDayOfWeekResponseDTO;
import com.fedex.geopolitical.dtoresponse.swaggerdtos.DaysOfWeekResponseAll;
import com.fedex.geopolitical.model.RefDayOfWeek;
import com.fedex.geopolitical.service.SearchService;

@RestController
public class DaysOfWeekController implements DaysOfWeekApi, GeopoliticalApiV2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(DaysOfWeekController.class);
	@Autowired
	private SearchService<String, RefDayOfWeekResponseDTO, RefDayOfWeek> refDayOfWeekService;

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<DaysOfWeekResponseAll> getRefDaoOfWeeks(String X_CSR_SECURITY_TOKEN, String contentType,
			String accept) {
		DaysOfWeekResponseAll response = null;

		try {
			response = new DaysOfWeekResponseAll(refDayOfWeekService.search(null));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
