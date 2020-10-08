package com.fedex.geopolitical.api.v2.controller;

import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.fedex.geopolitical.dto.LanguageDTO;
import com.fedex.geopolitical.dtoresponse.RefLanguageResponseDTO;
import com.fedex.geopolitical.dtoresponse.swaggerdtos.LanguageResponseAll;
import com.fedex.geopolitical.model.RefLanguage;
import com.fedex.geopolitical.service.SearchService;

@RestController
public class LanguagesController implements LanguagesApi, GeopoliticalApiV2 {
	private static final Logger LOGGER = LoggerFactory.getLogger(LanguagesController.class);
	@Autowired
	private SearchService<LanguageDTO, RefLanguageResponseDTO, RefLanguage> refLanguageService;

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<LanguageResponseAll> getLanguage(String X_CSR_SECURITY_TOKEN, String contentType,
			String accept, String countryCode, String localeCode) {
		LanguageResponseAll response = null;
		LanguageDTO languageDTO = new LanguageDTO();
		try {
			languageDTO.setCountryCd(countryCode);
			languageDTO.setLocaleCd(localeCode);
			response = new LanguageResponseAll(refLanguageService.search(languageDTO));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<LanguageResponseAll> getLanguageBYLandcd(String accept, String X_CSR_SECURITY_TOKEN,
			@Pattern(regexp = "^[a-zA-Z]{1,3}$") String langCd, String contentType) {
		LanguageResponseAll response = null;
		LanguageDTO languageDTO = new LanguageDTO();
		try {
			languageDTO.setLangCd(langCd);
			response = new LanguageResponseAll(refLanguageService.search(languageDTO));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}