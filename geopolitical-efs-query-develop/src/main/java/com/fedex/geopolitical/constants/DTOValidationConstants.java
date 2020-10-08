package com.fedex.geopolitical.constants;

public class DTOValidationConstants {
	
	private DTOValidationConstants(){}
	
	public static final long MAX_NUMBER_ID = (long) 99999999999999999999999999999999999999d;
	public static final int MIN_NUMBER_ID = 1;
	public static final int MAX_DIGIT = 38;
	public static final String MAX_LENGTH_SIZE_EXCEEDED = "Maximum Number Size Exceeded";
	public static final String NUMBER_CANNOT_BE_BLANK = "Cannot be blank!";
	public static final String DATE_FORMAT = "MM-dd-yyyy";
	public static final String COUNTRY_NUMBER_CD_NOT_NULL = "Country Number Cd cannot be a null field!";
	public static final String BLANK_COUNTRY_CD = "CountryCd cannot be blank!";
	public static final String BLANK_THREE_CHAR_COUNTRY_CD = "Three char country Cd cannot be blank!";
	public static final String BLANK_FIRST_WORK_WEEK_DAY_NAME = "First Work Week Day Name cannot be blank!";
	public static final String BLANK_LAST_WORK_WEEK_DAY_NAME = "Last Work Week Day Name cannot be blank!";
	public static final String BLANK_WEEKEND_FIRST_DAY_NAME = "Weekend First Day Name cannot be blank!";
	public static final String COUNTRY_CD_LESS_THAN_2_CHAR = "CountryCd must be less than 2 characters!";
	public static final String THREE_CHAR_COUNTRY_CD_LESS_THAN_3_CHAR = "Three char country Cd must be less than 3 characters!";
	public static final String INDEPENDENT_FLAG_LESS_THAN_1_CHAR = "Independent Flag must be less than 1 characters!";
	public static final String POSTAL_FORMAT_DESC_LESS_THAN_25_CHAR = "Postal Format Desc must be less than 25 characters!";
	public static final String POSTAL_FLAG_LESS_THAN_1_CHAR = "Postal Flag must be less than 1 characters!";
	public static final String FIRST_WORK_WEEK_DAY_NAME_LESS_THAN_25_CHAR = "First Work Week Day Name must be less than 25 characters!";
	public static final String LAST_WORK_WEEK_DAY_NAME_LESS_THAN_25_CHAR = "Last Work Week Day Name must be less than 25 characters!";
	public static final String WEEKEND_FIRST_DAY_NAME_LESS_THAN_25_CHAR = "Weekend First Day Name must be less than 25 characters!";
	public static final String INTERNET_DOMAIN_NAME_LESS_THAN_5_CHAR = "Internet Domain Name must be less than 5 characters!";
	
	public static final String DATE_VALIDATION_EXCEPTION = "Target Date must be less than equal to End Date!";
	
	public static final String BLANK_LANGUAGE_CD = "LanguageCd cannot be blank!";
	public static final String LANGUAGE_CD_LESS_THAN_2_CHAR = "LanguageCd must be less than 2 characters!";
	public static final String TRANSLATION_NAME_LESS_THAN_120_CHAR = "TranslationName must be less than 120 characters!";
	public static final String BLANK_LOCALE_CD = "LocaleCd cannot be blank!";
	public static final String LOCALE_CD_LESS_THAN_18_CHAR = "LocaleCd must be less than 18 characters!";
	public static final String CURRENCY_NUMBER_CD_NOT_NULL = "Currency Number Cd cannot be a null field!";
	public static final String BLANK_CURRENCY_CD = "Currency Cd cannot be blank!";
	public static final String CURRENCY_CD_LESS_THAN_3_CHAR = "Currency Cd must be less than 3 characters!";
	public static final String MONEY_FORMAT_DESC_LESS_THAN_18_CHAR = "Money Format Desc must be less than 18 characters!";
	public static final String MINOR_UNIT_CD_NOT_NULL = "Minor Unit Cd cannot be a null field!";
	public static final String BLANK_INITIAL_DIALING_PREFIX_CD = "Initial Dialing Prefix Cd cannot be blank!";
	public static final String INITIAL_DIALING_PREFIX_CD_LESS_THAN_7_CHAR = "Initial Dialing Prefix Cd must be less than 7 characters!";
	public static final String BLANK_INITIAL_DIALING_CD = "Initial Dialing Cd cannot be blank!";
	public static final String INITIAL_DIALING_CD_LESS_THAN_3_CHAR = "Initial Dialing Cd must be less than 3 characters!";
	
	public static final String DATE_FULL_FORMAT_DESC_LESS_THAN_65_CHAR = "DateFullFormatDesc must be less than 65 characters!";
	public static final String DATE_LONG_FORMAT_DESC_LESS_THAN_65_CHAR = "DateLongFormatDesc must be less than 65 characters!";
	public static final String DATE_MEDIUM_FORMAT_DESC_LESS_THAN_65_CHAR = "DateMediumFormatDesc must be less than 65 characters!";
	public static final String DATE_SHORT_FORMAT_DESC_LESS_THAN_65_CHAR = "DateShortFormatDesc must be less than 65 characters!";
	
	public static final String GEOPOLITICAL_TYPE_NOT_NULL = "GeopoliticalType cannot be a null field!";
	
	public static final String BLANK_NAME = "Name cannot be blank!";
	public static final String BLANK_USERNAME = "Username cannot be blank!";
	public static final String NAME_LESS_THAN_25_CHAR = "Name must be less than 25 characters!";
	public static final String USER_NOT_NULL = "User cannot be a null field!";
	public static final String GEOPL_TYPE_ID_NOT_NULL = "GeopoliticalTypeId cannot be a null field!";
	public static final String BLANK_GEOPL_RLTSP_TYPE_CD = "GeopoliticalRelationshipTypeCd cannot be blank!";
	public static final String BLANK_AREA_RLTSP_TYPE_DESC = "AreaRelationshipTypeDescription cannot be blank!";
	public static final String GEOPL_RLTSP_TYPE_CD_LESS_THAN_20_CHAR = "GeopoliticalRelationshipTypeCd must be less than 20 characters!";
	public static final String AREA_RLTSP_TYPE_DESC_LESS_THAN_100_CHAR = "AreaRelationshipTypeDescription must be less than 100 characters!";
	public static final String BLANK_GEOPL_TYPE_NAME = "GeopoliticalTypeName cannot be blank!";
	public static final String GEOPL_TYPE_NAME_LESS_THAN_50_CHAR = "GeopoliticalTypeName must be less than 50 characters!";
	public static final String DEPENDENT_RELATIONSHIP_ID_NOT_NULL = "DependentRelationshipId cannot be a null field!";
	public static final String BLANK_DEPENDENT_RELATIONSHIP_DESCRIPTION = "DependentRelationshipDescription cannot be blank!";
	public static final String DEPENDENT_RELATIONSHIP_DESCRIPTION_LESS_THAN_65_CHAR = "DependentRelationshipDescription must be less than 65 characters!";
	
	
	public static final String REF_SCRIPT_TYPE_CD_NOT_NULL = "sciptCd cannot be a null field!";
	public static final String BLANK_SCRIPT_NAME = "Script Name cannot be a blank or null field!";
	public static final String BLANK_SCRIPT_DESC = "Script Desc cannot be blank!";
	public static final String SCRPT_CD_NOT_MORE_THAN_EIGHTEEN = "Script Code must be less than 18 characters!";
	public static final String SCRPT_CD_NAME_SIZE="Script Name must be less than or equal to 256 characters";
	public static final String SCRPT_CD_DESC_SIZE="Script Description must be less than or equal to 4000 characters";
	
	public static final String BLANK_UOM_TYPE_CD = "UOM Type Cd cannot be a blank or null field!";
	public static final String UOM_TYPE_CD_LESS_THAN_10_CHAR = "UOM Type Cd must be less than 50 characters!";
	public static final String REF_UOM_TYPE_CD_NOT_NULL = "UOM Code cannot be a null field!";
	public static final String REF_UOM_TYPE_DESC_NOT_BLANK = "UOM Type Desc cannot be blank!";
	public static final String BLANK_UOM_TYPE_NAME = "Uom Type Name cannot be a blank or null field!";
	public static final String REF_UOM_TYPE_NAME_SIZE="Uom Type name must be less than or equal to 256 characters";
	public static final String REF_UOM_TYPE_DESC_SIZE="Uom Type name must be less than or equal to 1000 characters";
	public static final String DATATYPE_SIZE_EXCEEDED_UOM_TYPE_CD="UOM Type Code must be less than or equal to 10 characters!";
	public static final String DATATYPE_SIZE_EXCEEDED_UOM_TYPE_NM="UOM Type Name must be less than or equal to 256 characters!";
	public static final String DATATYPE_SIZE_EXCEEDED_UOM_TYPE_DESC="UOM Type Desc must be less than or equal to 1000 characters!";
	public static final String DATATYPE_SIZE_EXCEEDED="Datatype size exceeded";
	
	public static final String GEOPL_ORG_STD_CD_NOT_NULL = "Organization Standard Code cannot be a null or blank field!";
	public static final String ORG_STD_CD_NOT_MORE_THAN_TEN = "Organization Standard Code must be less than or equal to 10 characters!";
	public static final String ORG_STD_NM_NOT_MORE_THAN_SIXTY_FIVE = "Organization Standard Name must be less than or equal to 65 characters!";
	public static final String BLANK_GEOPL_ORG_STD_NAME = "Organization Standard Name cannot be blank!";
	
	public static final String REF_LANGUAGE_CD_NOT_NULL = "Language code cannot be a null field!";
	public static final String LANG_NAME_NOT_NULL = "Language Name cannot be a null field!";
	public static final String SCRIPT_CODE_NOT_FOUND_ERROR_MESSAGE="Script Code not found";
	public static final String DAY_OF_WEEK_NOT_FOUND_ERROR_MESSAGE="Day Of Week not found";
	public static final String MTH_OF_YEAR_NOT_FOUND_ERROR_MESSAGE="Month of Year does not exist";
	public static final String THREE_CHAR_LANG_CD_CANNOT_EXCEED_THREE_CHARS="Three character language code must consist of three characters";
	public static final String CANNOT_EXCEED_TWO_CHARS="Language code must consist of two characters";
	public static final String CANNOT_EXCEED_TWELVE_CHARS="cannot have more than twelve elements";
	public static final String CANNOT_EXCEED_SEVEN_CHARS="cannot have more than seven elements";
	public static final String REF_LANGUAGE_CD_NOT_BLANK= "Language Code cannot be blank!";
	public static final String REF_LANGUAGE_NAME_SIZE="Language Name must be less than or equal to 256 characters";
	public static final String REF_LANGUAGE_NATIVE_SCRPT_SIZE="Native Script Language Name must be less than or equal to 256 characters";
	public static final String SCRIPT_CODE_SIZE="Script Code must be less than or equal to 18 characters";
	public static final String LANG_DESC_SIZE="Language Description must be less than or equal to 4000 characters";
	
	public static final String HOLIDAY_ID_NOT_NULL = "Holiday Id cannot be a null field!";
	public static final String HOLIDAY_DATE_TEXT_NOT_NULL = "Holiday Date Param Text cannot be a null field!";
	public static final String HOLIDAY_NAME_NOT_NULL = "Holiday Name cannot be a null field!";
	public static final String BLANK_HOLIDAY_NAME = "Holiday Name cannot be blank!";
	public static final String MOY_ID_NOT_NULL = "MOY Number cannot be a null field!";
	public static final String MOY_NAME_NOT_BLANK = "MOY Short Name cannot be blank!";
	public static final String DOW_ID_NOT_NULL = "DOW Number cannot be a null field!";
	public static final String DOW_NAME_NOT_NULL = "DOW Short Name cannot be a null field!";
	public static final String DUPLICATE_ERROR_MESSAGE="Record already exists.";
	public static final String RESOURCE_NOT_FOUND_ERROR_MESSAGE="Record not found";
	public static final String NOT_FOUND_GEOPOLITICAL_TYPE_NAME = "GeopoliticalTypeName not found";
	public static final String NOT_FOUND_DEPENDENT_COUNTRY_RELATIONSHIP_ID = "DependentCountryRelationshipId not found";
	public static final String NOT_FOUND_UOM_TYPE_NAME = "UOMTypeName not found";
	public static final String NOT_FOUND_HOLIDAY_NAME = "HolidayName not found";
	public static final String NOT_FOUND_AFFIL_TYPE_NAME = "AffilTypeName not found";
	public static final String NOT_FOUND_LANGUAGE_ID = "LanguageId not found";
	public static final String NOT_FOUND_COUNTRY_CD = "CountryCd not found";
	public static final String NOT_FOUND_GEOPOLITICAL_TYPE = "GeopoliticalType not found";
	public static final String DOW_NBR_VALUES = "Date of week number must be equals to or between [1,7]";
	
	public static final String GEOPL_AFFIL_ID_NOT_NULL = "affilTypeId cannot be a null field!";
	public static final String GEOPL_AFFIL_CODE_NOT_NULL = "affilTypeCode cannot be a null field!";
	public static final String GEOPL_AFFIL_NAME_SIZE = "affilTypeName must be less than 65 characters";
	public static final String BLANK_GEOPL_AFFIL_NAME = "affilTypeName cannot be blank!";
	
	public static final String HOLIDAY_NAME_MAX_SIZE="HolidayName must be less than 65 characters";
	public static final String HOLIDAY_DATE_PARAM_TEXT_MAX_SIZE="Holiday date param text must be less than 400 characters";
	
	public static final String MONTH_SHORT_NAME_SIZE="Month Of Year short name must be less than 18 characters";
	public static final String DOW_FULL_NAME_SIZE="DOW FUll name must be less than 257 characters";
	public static final String DOW_SHORT_NAME_SIZE="DOW short name must be less than 10 characters";
	public static final String GEOPL_AFFIL_CODE_SIZE = "affilTypeCode must be less than 10 characters";
	
	public static final String FROM_NOT_NULL = "From Geopolitical Id cannot be a null field!";
	public static final String TO_NOT_NULL = "To Geopolitical Id cannot be a null field!";
	public static final String RELATIONSHIP_CODE_NOT_NULL = "Relationship type Code cannot be a null field!";
	
	public static final String COUNTRY_NOT_FOUND_MESSAGE = "Country not found";
	public static final String STATE_NOT_FOUND_MESSAGE = "State not found";
	
	public static final String ST_PROV_CD_NOT_BLANK = "State Province Code cannot be blank!";
	public static final String ST_PROV_NM_NOT_BLANK = "State Province Name cannot be blank!";
	public static final String GEOPL_ID_NOT_NULL = "Geopolitical Id cannot be a null field!";
	public static final String ORG_STD_CD_NOT_BLANK ="Organization Standard Code cannot be a null or blank field!";
	public static final String ST_PROV_NM_SIZE="State Province name must be less than or equal to 120 characters";
	public static final String GEOPL_ID_SIZE="Geopolitical ID must be less than or equal to 50 characters";
	public static final String ST_PROV_CD_SIZE="State Province Standard Code must be less than or equal to 10 characters";
	public static final String ORG_STD_CD_SIZE="Organization Standard Code must be less than or equal to 10 characters";
	public static final String ORG_STD_CD_NOT_FOUND_ERROR_MESSAGE="Organization Standard Code does not found";
	public static final String ST_CD_NOT_FOUND_ERROR_MESSAGE="State Code does not found";
	
	public static final String GEOPOL_RELATIONSHIP_MAX_SIZE="Relationship code  must be less than 20 characters ";
	public static final String COUNTRY_FULL_NAME_MAX_SIZE = "Country Full Name must be less than 120 characters";
	public static final String COUNTRY_SHORT_NAME_MAX_SIZE = "Country Short Name must be less than 65 characters";
	public static final String ORG_STD_CD_NAME_MAX_SIZE = "Org standard code must be less than 10 characters";
	public static final String GEOPL_ID_NAME_MAX_SIZE = "Geopolitical ID code must be less than 38 characters";
	public static final String ORG_STD_NOT_NULL = "Organisation Standard code cannot be a null field!";
	public static final String CNTRY_FULL_NM_NOT_NULL = "Country Full Name cannot be a null field!";
	public static final String CNTRY_SHRT_NM_NOT_BLANK = "Country Short Name cannot be blank!";
	public static final String GEOPL_ORG_CODE_NOT_EXISTING = "Geopolitical Organisation code not existing";
	public static final String GEOPOLITICAL_TYPE_CODE_NOT_FOUND = "Relationship Type code not found";
	public static final String GEOPOLITICAL_RLTSP_NOT_FOUND = "Geopolitical Relationship not found";
	public static final String STATE_ALREADY_EXISTS = "State already exists with GeopoliticalId:";
	public static final String INVALID_RELATIONSHIP_TYPE = "Invalid Relationship Type Code";
	
	public static final String ID_NOT_NULL = "Id cannot be null for update!";
	public static final String ID_WRONG_FORMAT = "Not correct format for Id!";
	public static final String UNABLE_PARSE_DATE = "Unable to parse Date";
	
	//Constants related to Effective and expiry date
	public static final String EFFECTIVE_DATE_NOT_NULL = "Effective Date cannot be a null field!";
	public static final String EFFECTIVE_DATE_CANNOT_LESS_THAN_EXISTING = "Effective Date must be less than existing effective date";
	public static final String EFFECTIVE_EXPIRY_DATE_MESSAGE="Effective Date must be less than Expiration Date";
	public static final String EFFECTIVE_CREATED_DATE_MESSAGE=" Effective Date must be greater than or equal to current date";
	public static final String EFFECTIVE_EXISTING_MAX_DATE="Effective Date must be greater than: ";
	public static final String EFFECTIVE_DATE_GIVEN_LESS_THAN = "Effective Date given less than";
	public static final String EFFECTIVE_DATE_VOILATION_ERROR_MESSAGE="Effective Date Voilation occured.";
	public static final String BLANK_EFFECTIVE_DATE = "Effective Date cannot be null!";
	public static final String BLANK_EXPIRATIO_DATE = "Expiration Date cannot be null!";
	public static final String EFFECTIVE_DT_MUST_BE_LESS_THAN_EXPIRY_DT = "Effective Date must be less than Expiration Date";
	public static final String EFFECTIVE_DT_MUST_BE_GREATER_THAN_EQUAL_TO_CURRENCT_DATE = " Effective Date must be greater than or equal to current date";
	public static final String EFFECTIVE_DATE_CANNOT_BE_NULL = "Effective Date cannot be a null field!";
	public static final String TRNSLDOW_SEVEN_ELEMENTS = "Translation Day Of Week must have 0 or 7 elements";
	public static final String TRNSLMOY_TWELVE_ELEMENTS = "Translation Month Of Year must have 0 or 12 elements";
	public static final String GEOPL_ENTITY_NOT_FOUND = "Geopolitical Entity not found";
	
}

