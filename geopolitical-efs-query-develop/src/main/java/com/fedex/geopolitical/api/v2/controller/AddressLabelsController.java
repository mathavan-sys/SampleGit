package com.fedex.geopolitical.api.v2.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.fedex.geopolitical.constants.DTOValidationConstants;
import com.fedex.geopolitical.dto.AddressServiceMapperDTO;
import com.fedex.geopolitical.dtoresponse.swaggerdtos.AddressLabelsResponseAll;
import com.fedex.geopolitical.exception.DateValidationException;
import com.fedex.geopolitical.model.AddressLabel;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;

@RestController
public class AddressLabelsController implements AddressLabelsApi, GeopoliticalApiV2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddressLabelsController.class);
	@Autowired
	private SearchService<AddressServiceMapperDTO, Object, AddressLabel> addressService;

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<AddressLabelsResponseAll> getAddress(String X_CSR_SECURITY_TOKEN, String contentType,
			String accept, String targetDate, String endDate) {
		AddressServiceMapperDTO addressServiceMapperDTO = new AddressServiceMapperDTO();

		try {
			Date tDate = CommonUtility.checkValidDate(targetDate);
			Date eDate = CommonUtility.checkValidDate(endDate);
			if ((endDate != null && targetDate != null) && eDate.compareTo(tDate) < 0) {
				throw new DateValidationException(DTOValidationConstants.DATE_VALIDATION_EXCEPTION);
			}

			addressServiceMapperDTO.setEffectiveDate(tDate);
			addressServiceMapperDTO.setExpirationDate(eDate);

			AddressLabelsResponseAll response = new AddressLabelsResponseAll(
					addressService.search(addressServiceMapperDTO));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<AddressLabelsResponseAll> getAddressByGeopoliticalId(String X_CSR_SECURITY_TOKEN,
			String geopoliticalId, String contentType, String accept, String targetDate, String endDate) {
		AddressServiceMapperDTO addressServiceMapperDTO = new AddressServiceMapperDTO();
		try {
			Date tDate = CommonUtility.checkValidDate(targetDate);
			Date eDate = CommonUtility.checkValidDate(endDate);
			if ((endDate != null && targetDate != null) && eDate.compareTo(tDate) < 0) {
				throw new DateValidationException(DTOValidationConstants.DATE_VALIDATION_EXCEPTION);
			}

			addressServiceMapperDTO.setEffectiveDate(tDate);
			addressServiceMapperDTO.setExpirationDate(eDate);
			addressServiceMapperDTO.setGeopoliticalId(geopoliticalId);

			AddressLabelsResponseAll response = new AddressLabelsResponseAll(
					addressService.search(addressServiceMapperDTO));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<AddressLabelsResponseAll> getAddressByGeopoliticalIdAndLocale(String X_CSR_SECURITY_TOKEN,
			String geopoliticalId, String localeCode, String contentType, String accept, String targetDate,
			String endDate) {
		AddressServiceMapperDTO addressServiceMapperDTO = new AddressServiceMapperDTO();
		try {
			Date tDate = CommonUtility.checkValidDate(targetDate);
			Date eDate = CommonUtility.checkValidDate(endDate);
			if ((endDate != null && targetDate != null) && eDate.compareTo(tDate) < 0) {
				throw new DateValidationException(DTOValidationConstants.DATE_VALIDATION_EXCEPTION);
			}

			addressServiceMapperDTO.setEffectiveDate(tDate);
			addressServiceMapperDTO.setExpirationDate(eDate);
			addressServiceMapperDTO.setGeopoliticalId(geopoliticalId);

			addressServiceMapperDTO.setLocaleCode(localeCode);
			AddressLabelsResponseAll response = new AddressLabelsResponseAll(
					addressService.search(addressServiceMapperDTO));
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<AddressLabelsResponseAll> getAddressByGeopoliticalIdAndLocaleAndLineNumber(
			String X_CSR_SECURITY_TOKEN, String geopoliticalId, String localeCode, Integer addressLineNumber, 
			String contentType, String accept, String targetDate, String endDate) {
		AddressServiceMapperDTO addressServiceMapperDTO = new AddressServiceMapperDTO();
		try {
			Date tDate = CommonUtility.checkValidDate(targetDate);
			Date eDate = CommonUtility.checkValidDate(endDate);
			if ((endDate != null && targetDate != null) && eDate.compareTo(tDate) < 0) {
				throw new DateValidationException(DTOValidationConstants.DATE_VALIDATION_EXCEPTION);
			}

			addressServiceMapperDTO.setEffectiveDate(tDate);
			addressServiceMapperDTO.setExpirationDate(eDate);
			addressServiceMapperDTO.setGeopoliticalId(geopoliticalId);
			addressServiceMapperDTO.setLocaleCode(localeCode);
			addressServiceMapperDTO.setAddressLineNumber(addressLineNumber);
			AddressLabelsResponseAll response = new AddressLabelsResponseAll(
					addressService.search(addressServiceMapperDTO));
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
