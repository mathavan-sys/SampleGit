package com.fedex.geopolitical.dto;

import java.util.Date;

import lombok.Data;

@Data
public class AddressServiceMapperDTO {

	private String geopoliticalId;
	private String localeCode;
	private int addressLineNumber;
	private Date effectiveDate;
	private Date expirationDate;

}
