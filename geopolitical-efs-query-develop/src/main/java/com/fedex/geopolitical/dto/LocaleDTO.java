package com.fedex.geopolitical.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class LocaleDTO {

	private String localeCode;
	private String languageCode;	
	@JsonInclude(Include.NON_NULL)
	private String geopoliticalId;	
	private String scriptCode;
	private Date cldrVersionDate;
	private String cldrVersionNumber;
	private String dateFullFormatDescription;
	private String dateLongFormatDescription;
	private String dateMediumFormatDescription;
	private String dateShortFormatDescription;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")	
	private Date effectiveDate;
	@JsonFormat(shape=Shape.STRING,pattern="yyyy-MM-dd")	
	private Date expirationDate;
	private List<TrnslDowDTO> translatedDOWs;
	private List<TrnslMthOfYrDTO> translatedMOYs;
	//private List<CountryServiceMapperDTO> country;
}
