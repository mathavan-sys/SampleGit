package com.fedex.geopolitical.api.v1.dto;

import com.fedex.geopolitical.model.Country;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountryServiceDTO {

	private Country country;
	private CountryServiceMapperDTO countryServiceMapperDTO;
	
}
