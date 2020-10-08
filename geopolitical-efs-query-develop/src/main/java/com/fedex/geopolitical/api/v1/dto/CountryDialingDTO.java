package com.fedex.geopolitical.api.v1.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class CountryDialingDTO {
 	
	private String intialDialingPrefixCd;
	private String intialDialingCd;
	private Long landPhMaxLthNbr;
	private Long landPhMinLthNbr;
	private Long moblPhMaxLthNbr;
	private Long moblPhMinLthNbr;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")
	private Date effectiveDate;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")
	private Date expirationDate;
	
}
