package com.fedex.geopolitical.api.v1.dto;

import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class CurrencyDTO {

	private BigInteger currencyNumberCd;
	private String currencyCd;
	private BigInteger minorUnitCd;
	private String moneyFormatDescription;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")
	private Date effectiveDate;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")
	private Date expirationDate;
	
}
