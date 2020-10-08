package com.fedex.geopolitical.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;


/**
 * The persistent class for the CNTRY_ORG_STD database table.
 * 
 */
@Data
public class CountryOrganizationStandardDTO {
	
	private String organizationStandardCode;
	private String organizationStandardName;
	private String countryFullName;
	private String countryShortName;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")	
	private Date effectiveDate;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")	
	private Date expirationDate;

}