package com.fedex.geopolitical.dto;

import lombok.Data;

@Data
public class TrnslMthOfYrDTO {
	
	private long monthOfYearNumber;
	private String translatedMonthOfYearName;
	private String localeCode;	

}
