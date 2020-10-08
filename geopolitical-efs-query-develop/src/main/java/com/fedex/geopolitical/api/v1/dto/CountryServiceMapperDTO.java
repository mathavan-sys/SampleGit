package com.fedex.geopolitical.api.v1.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CountryServiceMapperDTO {

	private Long geopoliticalId;
	private String countryCd;
	private String countryShortName;
	private String orgStandardCode;
	private Date effectiveDate;
	private Date expirationDate; 
	
}
