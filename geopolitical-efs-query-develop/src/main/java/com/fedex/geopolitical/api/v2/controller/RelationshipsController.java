/*package com.fedex.geopolitical.api.v2.controller;

import java.text.SimpleDateFormat;
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

import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dto.GeopoliticalRelationshipDTO;
import com.fedex.geopolitical.dtoresponse.GeopoliticalRelationshipResponseDTO;
import com.fedex.geopolitical.dtoresponse.swaggerdtos.GeopoliticalRelationshipResponseAll;
import com.fedex.geopolitical.model.GeoplRltsp;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;

@RestController
public class RelationshipsController implements RelationshipsApi, GeopoliticalApiV2 {
	private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipsController.class);

	@Autowired
	private SearchService<GeopoliticalRelationshipDTO, GeopoliticalRelationshipResponseDTO, GeoplRltsp> geoPoliticalRelationshipService;

	@Override
	@PreAuthorize("@authorizor.isAllowed(authentication.name,@securityConstant.geopoliticalQuery,@securityConstant.action)")
	public ResponseEntity<GeopoliticalRelationshipResponseAll> getRelationship(String X_CSR_SECURITY_TOKEN,
			String contentType, String accept,
			@Pattern(regexp = "^[a-zA-Z_ ]{1,20}$") @Valid String relationshipTypeCode,
			@Pattern(regexp = "[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]") @Valid String targetDate,
			@Pattern(regexp = "[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]") @Valid String endDate,
			@Pattern(regexp = "^[0-9]{1,50}$") @Valid String fromGeopoliticalId,
			@Pattern(regexp = "^[0-9]{1,50}$") @Valid String toGeopoliticalId) {
		try {
			GeopoliticalRelationshipDTO geopoliticalRelationshipDTO = new GeopoliticalRelationshipDTO();
			if (null != fromGeopoliticalId)
				geopoliticalRelationshipDTO.setFromGeopoliticalId(fromGeopoliticalId);
			if (null != toGeopoliticalId)
				geopoliticalRelationshipDTO.setToGeopoliticalId(toGeopoliticalId);
			if (null != relationshipTypeCode)
				geopoliticalRelationshipDTO.setRelationshipTypeCode(relationshipTypeCode);
			if (targetDate != null && endDate != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(GenericConstants.DATE_FORMAT);
				Date date = sdf.parse(targetDate);
				geopoliticalRelationshipDTO.setEffectiveDate(date);
				date = sdf.parse(endDate);
				geopoliticalRelationshipDTO.setExpirationDate(date);
			} else if (targetDate == null && endDate == null) {
				geopoliticalRelationshipDTO.setEffectiveDate(CommonUtility.getCurrenctDate());
				geopoliticalRelationshipDTO.setExpirationDate(CommonUtility.getDefaultExpirationDate());
			} else if (targetDate != null && endDate == null) {
				SimpleDateFormat sdf = new SimpleDateFormat(GenericConstants.DATE_FORMAT);
				Date date = sdf.parse(targetDate);
				geopoliticalRelationshipDTO.setEffectiveDate(date);
				geopoliticalRelationshipDTO.setExpirationDate(CommonUtility.getDefaultExpirationDate());
			} else if (targetDate == null && endDate != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(GenericConstants.DATE_FORMAT);
				geopoliticalRelationshipDTO.setEffectiveDate(CommonUtility.getCurrenctDate());
				Date date = sdf.parse(endDate);
				geopoliticalRelationshipDTO.setExpirationDate(date);
			}
			GeopoliticalRelationshipResponseAll response = new GeopoliticalRelationshipResponseAll(
					geoPoliticalRelationshipService.search(geopoliticalRelationshipDTO));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

}
*/