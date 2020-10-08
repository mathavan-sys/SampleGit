package com.fedex.geopolitical.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;


/**
 * The persistent class for the HOLIDAY database table.
 * 
 */

@Data
public class GeoplAffilTypeDTO {
	
	private Long affiliationTypeId;
	private String affiliationTypeCode;
	private String affiliationTypeName;
	private User user;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")	
	private Date effectiveDate;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")	
	private Date expirationDate;

}