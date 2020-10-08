package com.fedex.geopolitical.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;


public interface CountryOrgsFlattennedDTO extends Serializable{
	String getGeopoliticalId();
	BigInteger getCountryNumericCode();
	String getCountryCode();
	String getThreeCharacterCountryCode(); 
	String getIndependentFlag();
	String getDependentRelationshipId();
	BigInteger getDependentCountryCode();
	String getPostalFormatDescription();
	String getPostalFlag();
	BigInteger getPostalLength();
	String getFirstWorkWeekDayName();
	String getLastWorkWeekDayName();
	String getWeekendFirstDayName();
	String getInternetDomainName();
	String getInternationalDialingCode();
	Long getLandPhoneMaximumLength();
	Long getLandPhoneMinimumLength();
	Long getMobilePhoneMaximumLength();
	Long getMobilePhoneMinimumLength();
	String getPhoneNumberFormatPattern();
	Date getEffectiveDate();
	Date getExpirationDate();
	BigInteger getCurrencyNumericCode();
	String getCurrencyCode();
	BigInteger getMinorUnitCode();
	String getMoneyFormatDescription();
	Date getCuEffectiveDate();
	Date getCuExpirationDate();
	String getOrganizationStandardCode();
	String getOrganizationStandardName();
	String getCountryFullName();
	String getCountryShortName();
	Date getCosEffectiveDate();
	Date getCosExpirationDate();
}
