package com.fedex.geopolitical.dtoresponse;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fedex.geopolitical.dto.TrnslDowDTO;
import com.fedex.geopolitical.dto.TrnslMthOfYrDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LocaleResponseDTO {

	private String localeCode;	
	private String countryCode;
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
	private List<TrnslDowResponseDTO> translatedDOWs;
	private List<TrnslMthOfYrResoponseDTO> translatedMOYs;
}
