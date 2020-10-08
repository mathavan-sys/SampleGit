package com.fedex.geopolitical.constants;

public class APIMappingConstants {

	private APIMappingConstants() {
	}

	public static final String GEOPOLITICAL = "/geopolitical";
	public static final String APPLICATION_JSON = "application/json";
	public static final String COUNTRY_GEOPOLITICAL_ID = "/countries/{geopoliticalIdString}";
	public static final String COUNTRY = "/countries";
	public static final String FIND_ALL_COUNTRY_DETAIL = "/findAllCountryDetails";
	public static final String GEOPOLITICAL_TYPE = "/geopoliticalTypes";
	public static final String RELATIONSHIP_TYPE = "/relationshipTypes";
	public static final String DEPENDENT_COUNTRY_RELATIONSHIP_TYPES = "/dependentCountryRelationshipTypes";
	public static final String DEPENDENT_COUNTRY_RELATIONSHIPS = "/dependentCountryRelationships/{dependentCountryCdString}";
	public static final String GEOPOLITICAL_TYPE_PUT = "/geopoliticalTypes/{geopoliticalTypeId}";
	public static final String GEOPL_AFFILIATION_TYPE_PUT = "/affiliationTypes/{affiliationTypeId}";

	public static final String GEOPL_ORG_STD = "/geopoliticalOrganizationStandards";

	public static final String RMOY = "/monthOfYear";
	public static final String RMOYS = "/monthsOfYear";

	public static final String GEOPOLITICAL_RELATIONSHIP = "/relationships";
	public static final String GEOPOLITICAL_RELATIONSHIP_TYPES_RTLSPTYCD = "/relationshipTypes/{relationshipTypeCode}";
	public static final String GEOPOLITICAL_RELATIONSHIP_TYPES = "/relationshipTypes";

	public static final String GEOPOLITICAL_ORG_STDS = "/geopoliticalOrganizationStandards";
	public static final String GEOPOLITICAL_ORG_STD_CD = "/geopoliticalOrganizationStandards/{orgStandardCode}";

	public static final String GEOPOLITICAL_TYPES = "/geopoliticalTypes";
	public static final String GEOPOLITICAL_TYPES_NAME = "/geopoliticalTypes/{geopoliticalTypeName}";

	public static final String HOLIDAY_NAME = "/holidays/{holidayName}";
	public static final String HOLIDAY = "/holidays";

	public static final String GEOPL_AFFILIATION_TYPE = "/affiliationTypes";
	public static final String GEOPL_AFFILIATION_TYPE_CD = "/affiliationTypes/{affilTypeCode}";

	public static final String REFERENCE_DAYS_WEEK = "/daysOfWeek";

	public static final String GEOPOLITICAL_LANGUAGES = "/languages";
	public static final String GEOPOLITICAL_LANGUAGES_CD = "/languages/{langCd}";

	public static final String REF_UOM_TYPE = "/uomTypes";
	public static final String REF_UOM_TYPE_CD = "/uomTypes/{uomTypeCd}";

	public static final String REF_SCRIPT = "/scripts";
	public static final String REF_SCRIPT_CD = "/scripts/{scrptCd}";

	public static final String CNTRY_ORG_STD = "/cntryOrgStds";
	public static final String ST_PROV_STD = "/stProvStds";
	public static final String ST_PROV_STD_GEOPOLITICALID = "/stProvStds/{geopoliticalId}";

	public static final String ADDRESS_LABEL = "/addressLabels";
	public static final String V2 = "/v2";

}
