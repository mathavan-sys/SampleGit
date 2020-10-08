package com.fedex.geopolitical.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrnslDowDTO {
	private long dayOfWeekNumber;
	private String translatedDayOfWeekName;
	private String localeCode;	
}
