package com.fedex.geopolitical.dtoresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TrnslMthOfYrResoponseDTO implements Comparable<TrnslMthOfYrResoponseDTO>{

	private String monthOfYearNumber;
	
	private String translatedMonthOfYearName;

	@Override
	public int compareTo(TrnslMthOfYrResoponseDTO moy)
	{
		if(Integer.parseInt(monthOfYearNumber) == Integer.parseInt(moy.getMonthOfYearNumber()))
		{
			return 0;
		}
		else if(Integer.parseInt(monthOfYearNumber) < Integer.parseInt(moy.getMonthOfYearNumber()))
		{
			return -1;
		}
		else
			return 1;
	}
}
