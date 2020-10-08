package com.fedex.geopolitical.dtoresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TrnslDowResponseDTO implements Comparable<TrnslDowResponseDTO>{
	
	private String dayOfWeekNumber;
	
	private String translatedDayOfWeekName;

	@Override
	public int compareTo(TrnslDowResponseDTO dow)
	{
		if(Integer.parseInt(dayOfWeekNumber) == Integer.parseInt(dow.getDayOfWeekNumber()))
		{
			return 0;
		}
		else if(Integer.parseInt(dayOfWeekNumber) < Integer.parseInt(dow.getDayOfWeekNumber()))
		{
			return -1;
		}
		else
			return 1;
	}

}
