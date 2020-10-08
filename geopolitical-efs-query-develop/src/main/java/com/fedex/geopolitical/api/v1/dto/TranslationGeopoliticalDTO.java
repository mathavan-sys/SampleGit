package com.fedex.geopolitical.api.v1.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class TranslationGeopoliticalDTO {

	private String languageCode;
	private String scrptCd;
	private String translationName;
	private Date versionDate;
	private String versionNumber;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")
	private Date effectiveDate;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")
	private Date expirationDate;
	
}
