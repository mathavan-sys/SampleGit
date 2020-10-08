package com.fedex.geopolitical.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CountryServiceMapperDTO {

	private String geopoliticalId;
	private String countryCode;
	private String countryShortName;
	private String organizationStandardCode;
	private Date effectiveDate;
	private Date expirationDate;

}
