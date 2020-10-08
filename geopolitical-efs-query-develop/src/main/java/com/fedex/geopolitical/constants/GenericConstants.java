package com.fedex.geopolitical.constants;

public class GenericConstants {
	
	private GenericConstants(){}

	public static final String VERSION = "1.0.0";
	public static final String REGEX_DATE_VALIDATION = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String NOT_PARSEABLE = "Unable to parse date";
	public static final String INVALID_DATE_FORMAT = "Invalid Date Format";
	public static final String CONSTANT_MSG_SUCCESS = "Success";
	public static final String CONSTANT_INTERNAL_MSG = "Records Fetched Successfully";
	public static final String NO_RECORDS = "There are no records for the specified criteria";
	public static final String META_VERSION = "1.0.0";
	public static final String HTTPS_ERROR_MESSAGE = "Unauthorized. Secure protocol required";
	public static final String INVALID_PROTOCOL= "Unsecure protocol HTTP";
	public static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";
	public final static String APPLICATION_JSON = "application/json";
	public static final String SECURITY_TOKEN = "X-CSR-SECURITY_TOKEN";
	public static final String META_SUCCESS_STATUS_CODE = "200";
	public static final String TRANSACTIONID = "transactionId";
}
