package com.fedex.geopolitical.api.v2.controller;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.fedex.geopolitical.constants.DTOValidationConstants;
import com.fedex.geopolitical.dto.CountryDTO;
import com.fedex.geopolitical.dto.CountryServiceDTO;
import com.fedex.geopolitical.dto.CountryServiceMapperDTO;
import com.fedex.geopolitical.dtoresponse.swaggerdtos.CountryResponseAll;
import com.fedex.geopolitical.exception.DateValidationException;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;

@RestController
public class CountriesController implements CountriesApi, GeopoliticalApiV2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(CountriesController.class);
	@Autowired
	private SearchService<CountryServiceMapperDTO, CountryDTO, CountryServiceDTO> countryService;

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<CountryResponseAll> getCountriesByGeopoliticalId(String X_CSR_SECURITY_TOKEN,
			@Pattern(regexp = "^[0-9a-zA-Z-]{1,50}$") String geopoliticalIdString, String contentType, String accept) {
		CountryServiceMapperDTO countryServiceMapperDTO = null;
		try {
			countryServiceMapperDTO = new CountryServiceMapperDTO();
			countryServiceMapperDTO.setGeopoliticalId(geopoliticalIdString);

			CountryResponseAll response = new CountryResponseAll(countryService.search(countryServiceMapperDTO));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<CountryResponseAll> getAllCountries(String X_CSR_SECURITY_TOKEN, String contentType,
			String accept, @Pattern(regexp = "^[0-9a-zA-Z-]{1,50}$") @Valid String geopoliticalId,
			@Pattern(regexp = "[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]") @Valid String targetDate,
			@Pattern(regexp = "[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]") @Valid String endDate,
			@Pattern(regexp = "^[a-zA-Z]{1,2}$") @Valid String countryCd, @Valid String countryShortName,
			@Pattern(regexp = "^[a-zA-Z]{1,10}$") @Valid String orgStandardCode) {
		CountryServiceMapperDTO countryServiceMapperDTO = null;
		try {
			countryServiceMapperDTO = new CountryServiceMapperDTO();
			Date tDate = CommonUtility.checkValidDate(targetDate);
			Date eDate = CommonUtility.checkValidDate(endDate);
			if ((endDate != null && targetDate != null) && eDate.compareTo(tDate) < 0) {
				throw new DateValidationException(DTOValidationConstants.DATE_VALIDATION_EXCEPTION);
			}

			if (geopoliticalId != null && !geopoliticalId.equals("")) {
				countryServiceMapperDTO.setGeopoliticalId(geopoliticalId);
			}

			countryServiceMapperDTO.setCountryCode(countryCd);
			countryServiceMapperDTO.setCountryShortName(countryShortName);
			countryServiceMapperDTO.setOrganizationStandardCode(orgStandardCode);
			countryServiceMapperDTO.setEffectiveDate(tDate);
			countryServiceMapperDTO.setExpirationDate(eDate);
			CountryResponseAll response = new CountryResponseAll(countryService.search(countryServiceMapperDTO));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
