package com.fedex.geopolitical.api.v1.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fedex.geopolitical.dto.TrnslDowDTO;
import com.fedex.geopolitical.dto.TrnslMthOfYrDTO;

import lombok.Data;

@Data
public class LocaleDTO {
	
	private String localeCd;
	private String scrptCd;
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
}
