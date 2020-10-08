package com.fedex.geopolitical.api.v1.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class CountryOrgsDTO {

	private Long geopoliticalId;
	private BigInteger countryNumberCd;
	private String countryCd;
	private String threeCharCountryCd;
	private String independentFlag;
	private String dependentRelationshipId;
	private BigInteger dependentCountryCd;
	private String postalFormatDescription;
	private String postalFlag;
	private BigInteger postalLengthNumber;
	private String firstWorkWeekDayName;
	private String lastWorkWeekDayName;
	private String weekendFirstDayName;
	private String internetDomainName;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")
	private Date effectiveDate;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")
	private Date expirationDate;
	private Set<CountryOrganizationStandardDTO> countryOrgStds; 
	private Set<CurrencyDTO> currencies; 
	
}
