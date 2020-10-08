package com.fedex.geopolitical.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;


/**
 * The persistent class for the GEOPL_RLTSP database table.
 * 
 */
@Data
public class GeopoliticalRelationshipDTO {
	
	private String fromGeopoliticalId;
	private String toGeopoliticalId;
	private String relationshipTypeCode;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")	
	private Date effectiveDate;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")	
	private Date expirationDate;
	private User user;
	
}