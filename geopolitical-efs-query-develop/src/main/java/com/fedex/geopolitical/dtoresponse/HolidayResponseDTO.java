package com.fedex.geopolitical.dtoresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HolidayResponseDTO {
	
	private String holidayId;
	private String holidayName;
	private String holidayDateParameterText;

}
