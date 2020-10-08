package com.fedex.geopolitical.api.v1.controller;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fedex.geopolitical.constants.APIMappingConstants;
import com.fedex.geopolitical.constants.DTOValidationConstants;
import com.fedex.geopolitical.api.v1.dto.CountryDTO;
import com.fedex.geopolitical.api.v1.dto.CountryServiceDTO;
import com.fedex.geopolitical.api.v1.dto.CountryServiceMapperDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.exception.DateValidationException;
import com.fedex.geopolitical.exception.LongFormatException;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;

@RestController
@RequestMapping(value= {"/","/v1"})
public class CountryControllerV1 {

	@Autowired
	private SearchService<CountryServiceMapperDTO, CountryDTO, CountryServiceDTO> countryServiceV1;

	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	@GetMapping(path = APIMappingConstants.COUNTRY_GEOPOLITICAL_ID, produces = APIMappingConstants.APPLICATION_JSON)
	public ResponseEntity<QueryServiceResponseDTO> getCountriesByGeopoliticalId(
			@PathVariable(required = false) String geopoliticalIdString)
			throws JsonProcessingException, ParseException {
		CountryServiceMapperDTO countryServiceMapperDTO = new CountryServiceMapperDTO();
		try {
			countryServiceMapperDTO.setGeopoliticalId(Long.parseLong(geopoliticalIdString));
		} catch (Exception e) {
			throw new LongFormatException(DTOValidationConstants.ID_WRONG_FORMAT);
		}

		QueryServiceResponseDTO response = countryServiceV1.search(countryServiceMapperDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	@GetMapping(path = APIMappingConstants.COUNTRY, produces = APIMappingConstants.APPLICATION_JSON)
	public ResponseEntity<QueryServiceResponseDTO> getCountriesByCountryCd(
			@RequestParam(required = false) String geopoliticalId, @RequestParam(required = false) String countryCd,
			@RequestParam(required = false) String targetDate, @RequestParam(required = false) String endDate,
			@RequestParam(required = false) String countryShortName,
			@RequestParam(required = false) String orgStandardCode) throws JsonProcessingException, ParseException {
		CountryServiceMapperDTO countryServiceMapperDTO = new CountryServiceMapperDTO();
		Date tDate = CommonUtility.checkValidDate(targetDate);
		Date eDate = CommonUtility.checkValidDate(endDate);
		if ((endDate != null && targetDate != null) && eDate.compareTo(tDate) < 0) {
			throw new DateValidationException(DTOValidationConstants.DATE_VALIDATION_EXCEPTION);
		}

		if (geopoliticalId != null && !geopoliticalId.equals("")) {
			try {
				countryServiceMapperDTO.setGeopoliticalId(Long.parseLong(geopoliticalId));
			} catch (Exception e) {
				throw new LongFormatException(DTOValidationConstants.ID_WRONG_FORMAT);
			}
		}

		countryServiceMapperDTO.setCountryCd(countryCd);
		countryServiceMapperDTO.setCountryShortName(countryShortName);
		countryServiceMapperDTO.setOrgStandardCode(orgStandardCode);
		countryServiceMapperDTO.setEffectiveDate(tDate);
		countryServiceMapperDTO.setExpirationDate(eDate);
		QueryServiceResponseDTO response = countryServiceV1.search(countryServiceMapperDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
