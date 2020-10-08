package com.fedex.geopolitical.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class RefLanguageDTO {
	
	private String languageCode;
	private String englishLanguageName;
	private String nativeScriptCode;
	private String nativeScriptLanguageName;
	private List<LocaleDTO> locales;
	private User user;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")	
	private Date effectiveDate;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")	
	private Date expirationDate;
	
}
