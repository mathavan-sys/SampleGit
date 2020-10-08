/*package com.fedex.geopolitical.api.v2.controller;

import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.fedex.geopolitical.dtoresponse.HolidayResponseDTO;
import com.fedex.geopolitical.dtoresponse.swaggerdtos.HolidayResponseAll;
import com.fedex.geopolitical.model.Holiday;
import com.fedex.geopolitical.service.SearchService;

@RestController
public class HolidaysController implements HolidaysApi, GeopoliticalApiV2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(HolidaysController.class);
	@Autowired
	private SearchService<String, HolidayResponseDTO, Holiday> holidayService;

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<HolidayResponseAll> getHolidays(String X_CSR_SECURITY_TOKEN, String contentType,
			String accept) {
		HolidayResponseAll response = null;

		try {
			response = new HolidayResponseAll(holidayService.search(null));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<HolidayResponseAll> getHolidaysOnName(String X_CSR_SECURITY_TOKEN,
			@Pattern(regexp = "^[a-zA-Z_' -]{1,65}$") String holidayName, String contentType, String accept) {
		HolidayResponseAll response = null;
		try {
			response = new HolidayResponseAll(holidayService.search(holidayName));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

}
*/