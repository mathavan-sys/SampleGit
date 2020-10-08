package com.fedex.geopolitical.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class GeopoliticalUnitOfMeasureDTO {

	private String uomTypeCode;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")	
	private Date effectiveDate;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")	
	private Date expirationDate;
	
}
