package com.fedex.geopolitical.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CountryDTO extends CountryOrgsDTO {

	private Set<GeopoliticalTypeDTO> geopoliticalType;
	private Set<GeopoliticalUnitOfMeasureDTO> geopoliticalUnitOfMeasures;
	private Set<GeopoliticalHolidayDTO> geopoliticalHolidays;
	private Set<GeopoliticalAffiliationDTO> geopoliticalAffiliations;
	private Set<TranslationGeopoliticalDTO> translationGeopoliticals;
	private Set<LocaleDTO> locales;
	private Set<CurrencyDTO> currencies;
	private Set<CountryOrganizationStandardDTO> countryOrganizationStandards;
	private HashMap<String, List<AddressLabelDTO>> addressLabels;

}
