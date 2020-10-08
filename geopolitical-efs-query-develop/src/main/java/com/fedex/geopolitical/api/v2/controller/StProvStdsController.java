/*package com.fedex.geopolitical.api.v2.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dto.StProvStdDTO;
import com.fedex.geopolitical.dtoresponse.StProvStdResponseDTO;
import com.fedex.geopolitical.dtoresponse.swaggerdtos.StateProvinceStandardResponseAll;
import com.fedex.geopolitical.exception.DateFormatException;
import com.fedex.geopolitical.model.StProvStd;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.service.impl.StProvStdServiceImpl;
import com.fedex.geopolitical.utility.CommonUtility;

@RestController
public class StProvStdsController implements StProvStdsApi, GeopoliticalApiV2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(StProvStdsController.class);

	@Autowired
	private SearchService<StProvStdDTO, StProvStdResponseDTO, StProvStd> stProvStdService;

	@Autowired
	StProvStdServiceImpl StProvStdServiceImpl;

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<StateProvinceStandardResponseAll> getStProvStd(String X_CSR_SECURITY_TOKEN,
			String contentType, String accept,
			@javax.validation.constraints.Pattern(regexp = "^[0-9]{1,50}$") @Valid String geopoliticalId,
			@javax.validation.constraints.Pattern(regexp = "[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]") @Valid String targetDate,
			@javax.validation.constraints.Pattern(regexp = "[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]") @Valid String endDate,
			@javax.validation.constraints.Pattern(regexp = "^[a-zA-Z]{1,10}$") @Valid String stProvCd,
			@javax.validation.constraints.Pattern(regexp = "^[a-zA-Z]{1,10}$") @Valid String orgStdCd,
			@javax.validation.constraints.Pattern(regexp = "^[a-zA-Z]{1,2}$") @Valid String countryCd) {
		try {
			StProvStdDTO stateStdDTO = new StProvStdDTO();
			stateStdDTO.setStateProvinceCode(stProvCd);
			stateStdDTO.setOrganizationStandardCode(orgStdCd);
			stateStdDTO.setCountryCode(countryCd);

			if (null != geopoliticalId)
				stateStdDTO.setGeopoliticalId(geopoliticalId);
			if (targetDate != null && endDate != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(GenericConstants.DATE_FORMAT);
				Date date = sdf.parse(targetDate);
				stateStdDTO.setEffectiveDate(date);
				date = sdf.parse(endDate);
				stateStdDTO.setExpirationDate(date);
			} else if (targetDate == null && endDate == null) {
				stateStdDTO.setEffectiveDate(CommonUtility.getCurrenctDate());
				stateStdDTO.setExpirationDate(CommonUtility.getDefaultExpirationDate());
			} else if (targetDate != null && endDate == null) {
				SimpleDateFormat sdf = new SimpleDateFormat(GenericConstants.DATE_FORMAT);
				Date date = sdf.parse(targetDate);
				stateStdDTO.setEffectiveDate(date);
				stateStdDTO.setExpirationDate(CommonUtility.getDefaultExpirationDate());
			} else if (targetDate == null && endDate != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(GenericConstants.DATE_FORMAT);
				stateStdDTO.setEffectiveDate(CommonUtility.getCurrenctDate());
				Date date = sdf.parse(endDate);
				stateStdDTO.setExpirationDate(date);
			}
			StateProvinceStandardResponseAll response = new StateProvinceStandardResponseAll(
					stProvStdService.search(stateStdDTO));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<StateProvinceStandardResponseAll> getStProvStdById(String X_CSR_SECURITY_TOKEN,
			@javax.validation.constraints.Pattern(regexp = "^[0-9]{1,50}$") String geopoliticalId, String contentType,
			String accept,
			@javax.validation.constraints.Pattern(regexp = "[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]") @Valid String targetDate,
			@javax.validation.constraints.Pattern(regexp = "[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]") @Valid String endDate) {
		try {
			Date target = checkValidDate(targetDate);
			Date end = checkValidDate(endDate);
			if (target == null) {
				target = CommonUtility.getCurrenctDate();
			}
			if (end == null) {
				end = CommonUtility.getDefaultExpirationDate();
			}
			StateProvinceStandardResponseAll response = new StateProvinceStandardResponseAll(
					StProvStdServiceImpl.search(geopoliticalId, target, end));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	private Date checkValidDate(String date) throws JsonProcessingException {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		Pattern pattern = Pattern.compile(GenericConstants.REGEX_DATE_VALIDATION);
		if (pattern.matcher(date).matches()) {
			SimpleDateFormat sdf = new SimpleDateFormat(GenericConstants.DATE_FORMAT);
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				LOGGER.info(GenericConstants.NOT_PARSEABLE);
			}
		} else {
			throw new DateFormatException(GenericConstants.INVALID_DATE_FORMAT);
		}
		return null;
	}

}
*/