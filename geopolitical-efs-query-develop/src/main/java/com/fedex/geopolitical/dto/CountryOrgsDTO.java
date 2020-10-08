package com.fedex.geopolitical.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.Set;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class CountryOrgsDTO {

	private String geopoliticalId;
	private BigInteger countryNumericCode;
	private String countryCode;
	private String threeCharacterCountryCode;
	private String independentFlag; 
	private String dependentRelationshipId;
	private BigInteger dependentCountryCode;
	private String postalFormatDescription;
	private String postalFlag;
	private BigInteger postalLength;
	private String firstWorkWeekDayName;
	private String lastWorkWeekDayName;
	private String weekendFirstDayName;
	private String internetDomainName;
	private String internationalDialingCode;
	private Long landPhoneMaximumLength;
	private Long landPhoneMinimumLength;
	private Long mobilePhoneMaximumLength;
	private Long mobilePhoneMinimumLength;
	private String phoneNumberFormatPattern;
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
	private Date effectiveDate;
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
	private Date expirationDate;
	private Set<CurrencyDTO> currencies;
	private Set<CountryOrganizationStandardDTO> countryOrganizationStandards;
    private HashMap<String, List<AddressLabelDTO>> addressLabels; 
}
