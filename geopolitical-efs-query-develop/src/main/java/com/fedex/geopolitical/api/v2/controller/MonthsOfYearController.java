package com.fedex.geopolitical.api.v2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.fedex.geopolitical.dtoresponse.RefMonthOfYearResponseDTO;
import com.fedex.geopolitical.dtoresponse.swaggerdtos.MonthsOfYearResponseAll;
import com.fedex.geopolitical.model.RefMonthOfYear;
import com.fedex.geopolitical.service.SearchService;

@RestController
public class MonthsOfYearController implements MonthsOfYearApi, GeopoliticalApiV2 {
	private static final Logger LOGGER = LoggerFactory.getLogger(MonthsOfYearController.class);
	@Autowired
	private SearchService<String, RefMonthOfYearResponseDTO, RefMonthOfYear> refMonthOfYearService;

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<MonthsOfYearResponseAll> getMonths(String X_CSR_SECURITY_TOKEN, String contentType,
			String accept) {
		MonthsOfYearResponseAll response = null;
		try {
			response = new MonthsOfYearResponseAll(refMonthOfYearService.search(null));
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
