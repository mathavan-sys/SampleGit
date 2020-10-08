package com.fedex.geopolitical.dto;

import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class CurrencyDTO {

	private BigInteger currencyNumericCode;
	private String currencyCode;
	private BigInteger minorUnitCode;
	private String moneyFormatDescription;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")	
	private Date effectiveDate;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")	
	private Date expirationDate;
	
}
