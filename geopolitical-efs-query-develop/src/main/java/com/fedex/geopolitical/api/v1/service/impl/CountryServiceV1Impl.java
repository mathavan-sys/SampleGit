package com.fedex.geopolitical.api.v1.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fedex.geopolitical.api.v1.dto.CountryDTO;
import com.fedex.geopolitical.api.v1.dto.CountryDialingDTO;
import com.fedex.geopolitical.api.v1.dto.CountryOrganizationStandardDTO;
import com.fedex.geopolitical.api.v1.dto.CountryOrgsDTO;
import com.fedex.geopolitical.api.v1.dto.CountryServiceDTO;
import com.fedex.geopolitical.api.v1.dto.CountryServiceMapperDTO;
import com.fedex.geopolitical.api.v1.dto.CurrencyDTO;
import com.fedex.geopolitical.api.v1.dto.GeopoliticalAffiliationDTO;
import com.fedex.geopolitical.api.v1.dto.GeopoliticalHolidayDTO;
import com.fedex.geopolitical.api.v1.dto.GeopoliticalTypeDTO;
import com.fedex.geopolitical.api.v1.dto.GeopoliticalUnitOfMeasureDTO;
import com.fedex.geopolitical.api.v1.dto.LocaleDTO;
import com.fedex.geopolitical.api.v1.dto.TranslationGeopoliticalDTO;
import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.model.CntryOrgStd;
import com.fedex.geopolitical.model.Country;
import com.fedex.geopolitical.model.CountryDialing;
import com.fedex.geopolitical.model.Currency;
import com.fedex.geopolitical.model.GeoplAffil;
import com.fedex.geopolitical.model.GeoplHday;
import com.fedex.geopolitical.model.GeoplUom;
import com.fedex.geopolitical.model.Locale;
import com.fedex.geopolitical.model.TrnslGeopl;
import com.fedex.geopolitical.repository.CntryDialRepository;
import com.fedex.geopolitical.repository.CntryOrgStdRepository;
import com.fedex.geopolitical.repository.CountryRepository;
import com.fedex.geopolitical.repository.CurrencyRepository;
import com.fedex.geopolitical.repository.GeoplAffilRepository;
import com.fedex.geopolitical.repository.GeoplHdayRepository;
import com.fedex.geopolitical.repository.GeoplUomRepository;
import com.fedex.geopolitical.repository.LocaleRepository;
import com.fedex.geopolitical.repository.RefLanguageRepository;
import com.fedex.geopolitical.repository.TrnslGeoplRepository;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;

import lombok.Setter;

@Service
@Setter
@Qualifier("countryServiceV1")
public class CountryServiceV1Impl implements SearchService<CountryServiceMapperDTO, CountryDTO, CountryServiceDTO> {

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	CntryOrgStdRepository cntryOrgStdRepository;

	@Autowired
	CntryDialRepository cntryDialRepository;

	@Autowired
	CurrencyRepository currencyRepository;

	@Autowired
	GeoplUomRepository geoplUOMRepository;

	@Autowired
	GeoplHdayRepository geoplHdayRepository;

	@Autowired
	GeoplAffilRepository geoplAffilRepository;

	@Autowired
	LocaleRepository localeRepository;

	@Autowired
	TrnslGeoplRepository trnslGeoplRepository;

	@Autowired
	RefLanguageRepository languageRepository;

	@Override
	public QueryServiceResponseDTO search(CountryServiceMapperDTO countryServiceMapperDTO) throws ParseException {
		List<Country> countries = new ArrayList<>();
		putDefaultEffectiveAndExpirationDate(countryServiceMapperDTO);
		if (countryServiceMapperDTO.getGeopoliticalId() != null) {
			countries = getCountriesByGeopoliticalIdAndDate(countryServiceMapperDTO.getGeopoliticalId(),
					countryServiceMapperDTO.getEffectiveDate(), countryServiceMapperDTO.getExpirationDate());
		} else if (countryServiceMapperDTO.getCountryCd() != null) {
			countries = getCountriesByCountryCd(countries, countryServiceMapperDTO);
		} else if (countryServiceMapperDTO.getCountryShortName() != null
				|| countryServiceMapperDTO.getOrgStandardCode() != null) {
			countries = getCountriesByFilters(countryServiceMapperDTO);
		} else {
			return getCountries(countries, countryServiceMapperDTO, true);
		}

		return getCountries(countries, countryServiceMapperDTO, false);
	}

	private QueryServiceResponseDTO getCountries(List<Country> countries,
			CountryServiceMapperDTO countryServiceMapperDTO, boolean isAllCountries) {
		if (isAllCountries)
			countries = getAllCountries(countries, countryServiceMapperDTO);
		List<CountryOrgsDTO> countryOrgsDTOs = new ArrayList<>();
		if (!CollectionUtils.isEmpty(countries)) {
			for (Country country : countries) {
				CountryOrgsDTO countryOrgsDTO = new CountryOrgsDTO();

				mapCountryOrg(countryOrgsDTO, country);
				mapCntryOrgStdsToCountryOrgs(countryOrgsDTO, country, countryServiceMapperDTO);
				mapCurrenciesToCountryOrgs(countryOrgsDTO, country, countryServiceMapperDTO);
				countryOrgsDTOs.add(countryOrgsDTO);
			}
		}
		return prepareCountriesReponse(countryOrgsDTOs);
	}

	private void mapCurrenciesToCountryOrgs(CountryOrgsDTO countryOrgsDTO, Country country,
			CountryServiceMapperDTO countryServiceMapperDTO) {
		Optional<List<Currency>> currencies = currencyRepository.findByGeopoliticalId(
				country.getId().getGeopoliticalId(), countryServiceMapperDTO.getEffectiveDate(),
				countryServiceMapperDTO.getExpirationDate());
		if (currencies.isPresent()) {
			Set<CurrencyDTO> currencyDTOs = new HashSet<>();
			for (Currency currency : currencies.get()) {
				CurrencyDTO currencyDTO = new CurrencyDTO();
				currencyDTO.setEffectiveDate(currency.getId().getEffectiveDate());
				currencyDTO.setExpirationDate(currency.getExpirationDate());
				currencyDTO.setCurrencyCd(currency.getCurrencyCd());
				currencyDTO.setCurrencyNumberCd(currency.getId().getCurrencyNumberCd());
				currencyDTO.setMinorUnitCd(currency.getMinorUnitCd());
				currencyDTO.setMoneyFormatDescription(currency.getMoneyFormatDescription());
				currencyDTOs.add(currencyDTO);
			}
			countryOrgsDTO.setCurrencies(currencyDTOs);
		}

	}

	private List<Country> getAllCountries(List<Country> countries, CountryServiceMapperDTO countryServiceMapperDTO) {
		Optional<List<Country>> countryList = countryRepository.findByDate(countryServiceMapperDTO.getEffectiveDate(),
				countryServiceMapperDTO.getExpirationDate());
		if (countryList.isPresent()) {
			countries = countryList.get();
		}
		return countries;
	}

	private List<Country> getCountriesByCountryCd(List<Country> countries,
			CountryServiceMapperDTO countryServiceMapperDTO) {
		Optional<Country> country = countryRepository.findFirstByCountryCd(countryServiceMapperDTO.getCountryCd());
		if (country.isPresent()) {
			countries = getCountriesByGeopoliticalIdAndDate(Long.parseLong(country.get().getId().getGeopoliticalId()),
					countryServiceMapperDTO.getEffectiveDate(), countryServiceMapperDTO.getExpirationDate());
		}
		return countries;
	}

	private List<Country> getCountriesByFilters(CountryServiceMapperDTO countryServiceMapperDTO) {
		List<CntryOrgStd> cntryOrgStds = cntryOrgStdRepository.findAll(new Specification<CntryOrgStd>() {

			@Override
			public Predicate toPredicate(Root<CntryOrgStd> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (countryServiceMapperDTO.getCountryShortName() != null)
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("cntryShtNm"),
							"%" + countryServiceMapperDTO.getCountryShortName() + "%")));
				if (countryServiceMapperDTO.getOrgStandardCode() != null)
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("geoplOrgStd").get("orgStdCd"),
							"%" + countryServiceMapperDTO.getOrgStandardCode() + "%")));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
		List<String> geopoliticalIds = cntryOrgStds.stream().map(x -> x.getId().getGeopoliticalId())
				.collect(Collectors.toList());

		return countryRepository.findByGeopoliticalIdsAndDate(geopoliticalIds,
				countryServiceMapperDTO.getEffectiveDate(), countryServiceMapperDTO.getExpirationDate());
	}

	private void putDefaultEffectiveAndExpirationDate(CountryServiceMapperDTO countryServiceMapperDTO) {
		if (countryServiceMapperDTO.getEffectiveDate() == null) {
			countryServiceMapperDTO.setEffectiveDate(CommonUtility.getCurrenctDate());
		}
		if (countryServiceMapperDTO.getExpirationDate() == null) {
			countryServiceMapperDTO.setExpirationDate(CommonUtility.getDefaultExpirationDate());
		}
	}

	private List<Country> getCountriesByGeopoliticalIdAndDate(long geopoliticalId, Date effectiveDate,
			Date expirationDate) {
		List<Country> countries = new ArrayList<>();
		Optional<List<Country>> countryOpt = countryRepository
				.findByGeopoliticalIdAndDate(String.valueOf(geopoliticalId), effectiveDate, expirationDate);
		if (countryOpt.isPresent()) {
			countries = countryOpt.get();
		}
		return countries;
	}

	public QueryServiceResponseDTO prepareCountriesReponse(List<CountryOrgsDTO> countryResponses) {
		MetaDTO metaResponse = new MetaDTO();
		QueryServiceResponseDTO queryServiceResponse = new QueryServiceResponseDTO();
		MessageDTO message = new MessageDTO();
		queryServiceResponse.setData(countryResponses);
		if (CollectionUtils.isEmpty(countryResponses)) {
			message.setInternalMessage(GenericConstants.NO_RECORDS);
		} else {
			message.setInternalMessage(GenericConstants.CONSTANT_INTERNAL_MSG);
		}
		CommonUtility.populateSuccess_Status_StatusCd_TS(message,metaResponse);
		metaResponse.setVersion(GenericConstants.META_VERSION);
		metaResponse.setMessage(message);
		queryServiceResponse.setMeta(metaResponse);
		return queryServiceResponse;
	}

	@Override
	public QueryServiceResponseDTO prepareReponse(List<CountryDTO> countryResponses) {
		MetaDTO metaResponse = new MetaDTO();
		QueryServiceResponseDTO queryServiceResponse = new QueryServiceResponseDTO();
		MessageDTO message = new MessageDTO();
		queryServiceResponse.setData(countryResponses);
		if (CollectionUtils.isEmpty(countryResponses)) {
			message.setInternalMessage(GenericConstants.NO_RECORDS);
		} else {
			message.setInternalMessage(GenericConstants.CONSTANT_INTERNAL_MSG);
		}
		CommonUtility.populateSuccess_Status_StatusCd_TS(message,metaResponse);
		metaResponse.setVersion(GenericConstants.META_VERSION);
		metaResponse.setMessage(message);
		queryServiceResponse.setMeta(metaResponse);
		return queryServiceResponse;
	}

	@Override
	public List<CountryDTO> map(List<CountryServiceDTO> countryServiceDTOs) throws ParseException {
		List<CountryDTO> countryDTOs = new ArrayList<>();
		if (!CollectionUtils.isEmpty(countryServiceDTOs)) {
			for (CountryServiceDTO countryServiceDTO : countryServiceDTOs) {

				Country country = countryServiceDTO.getCountry();
				CountryServiceMapperDTO countryServiceMapperDTO = countryServiceDTO.getCountryServiceMapperDTO();

				CountryDTO countryDTO = new CountryDTO();

				// mapping country
				mapCountry(countryDTO, country);

				// mapping countryDialings
				mapCountryDialings(countryDTO, country, countryServiceMapperDTO);

				// mapping currencies
				mapCurrencies(countryDTO, country, countryServiceMapperDTO);

				// mapping geopoliticalUOMs
				mapGeoplUOMs(countryDTO, country, countryServiceMapperDTO);

				// mapping geopoliticalHolidays
				mapGeoplHdays(countryDTO, country, countryServiceMapperDTO);

				// mapping geopoliticalAffils
				mapGeoplAffils(countryDTO, country, countryServiceMapperDTO);

				// mapping locales
				mapLocales(countryDTO, country, countryServiceMapperDTO);

				// mapping translationGeopoliticals
				mapTrnslGeopls(countryDTO, country, countryServiceMapperDTO);

				// mapping countryOrgStds
				mapCntryOrgStds(countryDTO, country, countryServiceMapperDTO);

				// mapping geopoliticalTypes
				mapGeopoliticalType(countryDTO, country);

				// adding countryDTO to list
				countryDTOs.add(countryDTO);
			}
		}
		return countryDTOs;
	}

	private void mapCountry(CountryDTO countryDTO, Country country) {
		countryDTO.setGeopoliticalId(Long.parseLong(country.getId().getGeopoliticalId()));
		countryDTO.setCountryNumberCd(country.getCountryNumberCd());
		countryDTO.setCountryCd(country.getCountryCd());
		countryDTO.setThreeCharCountryCd(country.getThreeCharCountryCd());
		countryDTO.setIndependentFlag(country.getIndependentFlag());
		countryDTO.setPostalFormatDescription(country.getPostalFormatDescription());
		countryDTO.setPostalFlag(country.getPostalFlag());
		countryDTO.setPostalLengthNumber(country.getPostalLengthNumber());
		countryDTO.setFirstWorkWeekDayName(country.getFirstWorkWeekDayName());
		countryDTO.setLastWorkWeekDayName(country.getLastWorkWeekDayName());
		countryDTO.setWeekendFirstDayName(country.getWeekendFirstDayName());
		countryDTO.setInternetDomainName(country.getInternetDomainName());
		countryDTO.setDependentRelationshipId(country.getDependentRelationshipId());
		countryDTO.setDependentCountryCd(country.getDependentCountryCd());
		countryDTO.setEffectiveDate(country.getId().getEffectiveDate());
		countryDTO.setExpirationDate(country.getExpirationDate());
	}

	private void mapCountryOrg(CountryOrgsDTO countryOrgsDTO, Country country) {
		countryOrgsDTO.setGeopoliticalId(Long.parseLong(country.getId().getGeopoliticalId()));
		countryOrgsDTO.setCountryNumberCd(country.getCountryNumberCd());
		countryOrgsDTO.setCountryCd(country.getCountryCd());
		countryOrgsDTO.setThreeCharCountryCd(country.getThreeCharCountryCd());
		countryOrgsDTO.setIndependentFlag(country.getIndependentFlag());
		countryOrgsDTO.setPostalFormatDescription(country.getPostalFormatDescription());
		countryOrgsDTO.setPostalFlag(country.getPostalFlag());
		countryOrgsDTO.setPostalLengthNumber(country.getPostalLengthNumber());
		countryOrgsDTO.setFirstWorkWeekDayName(country.getFirstWorkWeekDayName());
		countryOrgsDTO.setLastWorkWeekDayName(country.getLastWorkWeekDayName());
		countryOrgsDTO.setWeekendFirstDayName(country.getWeekendFirstDayName());
		countryOrgsDTO.setInternetDomainName(country.getInternetDomainName());
		countryOrgsDTO.setDependentRelationshipId(country.getDependentRelationshipId());
		countryOrgsDTO.setDependentCountryCd(country.getDependentCountryCd());
		countryOrgsDTO.setEffectiveDate(country.getId().getEffectiveDate());
		countryOrgsDTO.setExpirationDate(country.getExpirationDate());
	}

	private void mapCountryDialings(CountryDTO countryDTO, Country country,
			CountryServiceMapperDTO countryServiceMapperDTO) {
		Optional<List<CountryDialing>> countryDialings = cntryDialRepository.findByGeopoliticalId(
				country.getId().getGeopoliticalId(), countryServiceMapperDTO.getEffectiveDate(),
				countryServiceMapperDTO.getExpirationDate());
		if (countryDialings.isPresent()) {
			Set<CountryDialingDTO> countryDialingDTOs = new HashSet<>();
			for (CountryDialing countryDialing : countryDialings.get()) {
				CountryDialingDTO countryDialingDTO = new CountryDialingDTO();
				countryDialingDTO.setEffectiveDate(countryDialing.getId().getEffectiveDate());
				countryDialingDTO.setExpirationDate(countryDialing.getExpirationDate());
				countryDialingDTO.setIntialDialingPrefixCd(countryDialing.getId().getIntialDialingPrefixCd());
				countryDialingDTO.setIntialDialingCd(countryDialing.getIntialDialingCd());
				countryDialingDTO.setLandPhMaxLthNbr(countryDialing.getLandPhMaxLthNbr() != null
						? countryDialing.getLandPhMaxLthNbr().longValue() : null);
				countryDialingDTO.setLandPhMinLthNbr(countryDialing.getLandPhMinLthNbr() != null
						? countryDialing.getLandPhMinLthNbr().longValue() : null);
				countryDialingDTO.setMoblPhMaxLthNbr(countryDialing.getMoblPhMaxLthNbr() != null
						? countryDialing.getMoblPhMaxLthNbr().longValue() : null);
				countryDialingDTO.setMoblPhMinLthNbr(countryDialing.getMoblPhMinLthNbr() != null
						? countryDialing.getMoblPhMinLthNbr().longValue() : null);
				countryDialingDTOs.add(countryDialingDTO);
			}
			countryDTO.setCountryDialings(countryDialingDTOs);
		}
	}

	private void mapCurrencies(CountryDTO countryDTO, Country country,
			CountryServiceMapperDTO countryServiceMapperDTO) {
		Optional<List<Currency>> currencies = currencyRepository.findByGeopoliticalId(
				country.getId().getGeopoliticalId(), countryServiceMapperDTO.getEffectiveDate(),
				countryServiceMapperDTO.getExpirationDate());
		if (currencies.isPresent()) {
			Set<CurrencyDTO> currencyDTOs = new HashSet<>();
			for (Currency currency : currencies.get()) {
				CurrencyDTO currencyDTO = new CurrencyDTO();
				currencyDTO.setEffectiveDate(currency.getId().getEffectiveDate());
				currencyDTO.setExpirationDate(currency.getExpirationDate());
				currencyDTO.setCurrencyCd(currency.getCurrencyCd());
				currencyDTO.setCurrencyNumberCd(currency.getId().getCurrencyNumberCd());
				currencyDTO.setMinorUnitCd(currency.getMinorUnitCd());
				currencyDTO.setMoneyFormatDescription(currency.getMoneyFormatDescription());
				currencyDTOs.add(currencyDTO);
			}
			countryDTO.setCurrencies(currencyDTOs);
		}
	}

	private void mapGeoplHdays(CountryDTO countryDTO, Country country,
			CountryServiceMapperDTO countryServiceMapperDTO) {
		Optional<List<GeoplHday>> geoplHdays = geoplHdayRepository.findByGeopoliticalId(
				country.getId().getGeopoliticalId(), countryServiceMapperDTO.getEffectiveDate(),
				countryServiceMapperDTO.getExpirationDate());
		if (geoplHdays.isPresent()) {
			Set<GeopoliticalHolidayDTO> geopoliticalHolidayDTOs = new HashSet<>();
			for (GeoplHday geoplHday : geoplHdays.get()) {
				GeopoliticalHolidayDTO geopoliticalHolidayDTO = new GeopoliticalHolidayDTO();
				geopoliticalHolidayDTO.setHolidayName(geoplHday.getHoliday().getHolidayName());
				geopoliticalHolidayDTO.setEffectiveDate(geoplHday.getId().getEffectiveDate());
				geopoliticalHolidayDTO.setExpirationDate(geoplHday.getExpirationDate());
				geopoliticalHolidayDTOs.add(geopoliticalHolidayDTO);
			}
			countryDTO.setGeopoliticalHolidays(geopoliticalHolidayDTOs);
		}
	}

	private void mapGeoplUOMs(CountryDTO countryDTO, Country country, CountryServiceMapperDTO countryServiceMapperDTO) {
		Optional<List<GeoplUom>> geoplUoms = geoplUOMRepository.findByGeopoliticalId(
				country.getId().getGeopoliticalId(), countryServiceMapperDTO.getEffectiveDate(),
				countryServiceMapperDTO.getExpirationDate());
		if (geoplUoms.isPresent()) {
			Set<GeopoliticalUnitOfMeasureDTO> geopoliticalUnitOfMeasureDTOs = new HashSet<>();
			for (GeoplUom geoplUom : geoplUoms.get()) {
				GeopoliticalUnitOfMeasureDTO geopoliticalUnitOfMeasureDTO = new GeopoliticalUnitOfMeasureDTO();
				geopoliticalUnitOfMeasureDTO.setUomTypeCd(geoplUom.getId().getUomTypeCd());
				geopoliticalUnitOfMeasureDTO.setEffectiveDate(geoplUom.getId().getEffectiveDate());
				geopoliticalUnitOfMeasureDTO.setExpirationDate(geoplUom.getExpirationDate());
				geopoliticalUnitOfMeasureDTOs.add(geopoliticalUnitOfMeasureDTO);
			}
			countryDTO.setGeopoliticalUnitOfMeasures(geopoliticalUnitOfMeasureDTOs);
		}
	}

	private void mapGeoplAffils(CountryDTO countryDTO, Country country,
			CountryServiceMapperDTO countryServiceMapperDTO) {
		Optional<List<GeoplAffil>> geoplAffils = geoplAffilRepository.findByGeopoliticalId(
				country.getId().getGeopoliticalId(), countryServiceMapperDTO.getEffectiveDate(),
				countryServiceMapperDTO.getExpirationDate());
		if (geoplAffils.isPresent()) {
			Set<GeopoliticalAffiliationDTO> geopoliticalAffiliationDTOs = new HashSet<>();
			for (GeoplAffil geoplAffil : geoplAffils.get()) {
				GeopoliticalAffiliationDTO geopoliticalAffiliationDTO = new GeopoliticalAffiliationDTO();
				geopoliticalAffiliationDTO.setAffilTypeName(geoplAffil.getGeoplAffilType().getAffilTypeName());
				geopoliticalAffiliationDTO.setEffectiveDate(geoplAffil.getId().getEffectiveDate());
				geopoliticalAffiliationDTO.setExpirationDate(geoplAffil.getExpirationDate());
				geopoliticalAffiliationDTOs.add(geopoliticalAffiliationDTO);
			}
			countryDTO.setGeopoliticalAffiliations(geopoliticalAffiliationDTOs);
		}
	}

	private void mapLocales(CountryDTO countryDTO, Country country, CountryServiceMapperDTO countryServiceMapperDTO) {
		Optional<List<Locale>> locales = localeRepository.findByGeopoliticalId(country.getId().getGeopoliticalId(),
				countryServiceMapperDTO.getEffectiveDate(), countryServiceMapperDTO.getExpirationDate());
		if (locales.isPresent()) {
			Set<LocaleDTO> localeDTOs = new HashSet<>();
			for (Locale locale : locales.get()) {
				LocaleDTO localeDTO = new LocaleDTO();
				localeDTO.setLocaleCd(locale.getId().getLoclcode());
				if (locale.getRefScript() != null) {
					localeDTO.setScrptCd(locale.getRefScript().getScrptCd());
				}
				localeDTO.setCldrVersionDate(locale.getCldrVersionDate());
				localeDTO.setCldrVersionNumber(locale.getCldrVersionNumber());
				localeDTO.setDateFullFormatDescription(locale.getDateFullFormatDescription());
				localeDTO.setDateLongFormatDescription(locale.getDateLongFormatDescription());
				localeDTO.setDateShortFormatDescription(locale.getDateShortFormatDescription());
				localeDTO.setDateMediumFormatDescription(locale.getDateMediumFormatDescription());
				localeDTO.setEffectiveDate(locale.getId().getEffectiveDate());
				localeDTO.setExpirationDate(locale.getExpirationDate());
				localeDTOs.add(localeDTO);
			}
			countryDTO.setLocales(localeDTOs);
		}
	}

	private void mapTrnslGeopls(CountryDTO countryDTO, Country country,
			CountryServiceMapperDTO countryServiceMapperDTO) {
		Optional<List<TrnslGeopl>> trnslGeopls = trnslGeoplRepository.findByGeopoliticalId(
				country.getId().getGeopoliticalId(), countryServiceMapperDTO.getEffectiveDate(),
				countryServiceMapperDTO.getExpirationDate());
		if (trnslGeopls.isPresent()) {
			Set<TranslationGeopoliticalDTO> translationGeopoliticalDTOs = new HashSet<>();
			for (TrnslGeopl trnslGeopl : trnslGeopls.get()) {
				TranslationGeopoliticalDTO translationGeopoliticalDTO = new TranslationGeopoliticalDTO();
				translationGeopoliticalDTO.setTranslationName(trnslGeopl.getTranslationName());
				translationGeopoliticalDTO.setScrptCd(trnslGeopl.getRefScript().getScrptCd());
				translationGeopoliticalDTO.setVersionDate(trnslGeopl.getVersionDate());
				translationGeopoliticalDTO.setVersionNumber(trnslGeopl.getVersionNumber());
				translationGeopoliticalDTO.setEffectiveDate(trnslGeopl.getId().getEffectiveDate());
				translationGeopoliticalDTO.setExpirationDate(trnslGeopl.getExpirationDate());
				translationGeopoliticalDTOs.add(translationGeopoliticalDTO);
			}
			countryDTO.setTranslationGeopoliticals(translationGeopoliticalDTOs);
		}
	}

	private void mapCntryOrgStds(CountryDTO countryDTO, Country country,
			CountryServiceMapperDTO countryServiceMapperDTO) {
		Optional<List<CntryOrgStd>> cntryOrgStds = cntryOrgStdRepository.findByGeopoliticalId(
				country.getId().getGeopoliticalId(), countryServiceMapperDTO.getEffectiveDate(),
				countryServiceMapperDTO.getExpirationDate());
		if (cntryOrgStds.isPresent()) {
			Set<CountryOrganizationStandardDTO> CountryOrganizationStandardDTOs = new HashSet<>();
			for (CntryOrgStd cntryOrgStd : cntryOrgStds.get()) {
				CountryOrganizationStandardDTO countryOrganizationStandardDTO = new CountryOrganizationStandardDTO();
				countryOrganizationStandardDTO.setOrgStdCd(cntryOrgStd.getId().getOrgStdCd());
				countryOrganizationStandardDTO.setOrgStdNm(cntryOrgStd.getGeoplOrgStd().getOrgStdNm());
				countryOrganizationStandardDTO.setCountryFullName(cntryOrgStd.getCntryFullNm());
				countryOrganizationStandardDTO.setCountryShortName(cntryOrgStd.getCntryShtNm());
				countryOrganizationStandardDTO.setEffectiveDate(cntryOrgStd.getId().getEffectiveDate());
				countryOrganizationStandardDTO.setExpirationDate(cntryOrgStd.getExpirationDate());
				CountryOrganizationStandardDTOs.add(countryOrganizationStandardDTO);
			}
			countryDTO.setCountryOrgStds(CountryOrganizationStandardDTOs);
		}
	}

	private void mapCntryOrgStdsToCountryOrgs(CountryOrgsDTO countryOrgsDTO, Country country,
			CountryServiceMapperDTO countryServiceMapperDTO) {
		Optional<List<CntryOrgStd>> cntryOrgStds = cntryOrgStdRepository.findByGeopoliticalId(
				country.getId().getGeopoliticalId(), countryServiceMapperDTO.getEffectiveDate(),
				countryServiceMapperDTO.getExpirationDate());
		if (cntryOrgStds.isPresent()) {
			Set<CountryOrganizationStandardDTO> CountryOrganizationStandardDTOs = new HashSet<>();
			for (CntryOrgStd cntryOrgStd : cntryOrgStds.get()) {
				CountryOrganizationStandardDTO countryOrganizationStandardDTO = new CountryOrganizationStandardDTO();
				countryOrganizationStandardDTO.setOrgStdCd(cntryOrgStd.getId().getOrgStdCd());
				countryOrganizationStandardDTO.setOrgStdNm(cntryOrgStd.getGeoplOrgStd().getOrgStdNm());
				countryOrganizationStandardDTO.setCountryFullName(cntryOrgStd.getCntryFullNm());
				countryOrganizationStandardDTO.setCountryShortName(cntryOrgStd.getCntryShtNm());
				countryOrganizationStandardDTO.setEffectiveDate(cntryOrgStd.getId().getEffectiveDate());
				countryOrganizationStandardDTO.setExpirationDate(cntryOrgStd.getExpirationDate());
				CountryOrganizationStandardDTOs.add(countryOrganizationStandardDTO);
			}
			countryOrgsDTO.setCountryOrgStds(CountryOrganizationStandardDTOs);
		}
	}

	private void mapGeopoliticalType(CountryDTO countryDTO, Country country) {
		Set<GeopoliticalTypeDTO> geopoliticalTypeDTOs = new HashSet<>();
		/*
		 * for (Geopolitical geopolitical : country.getGeopoliticals()) {
		 * GeopoliticalTypeDTO geopoliticalTypeDTO = new GeopoliticalTypeDTO();
		 * geopoliticalTypeDTO.setGeopoliticalTypeName(geopolitical.getGeopoliticalType(
		 * ).getGeopoliticalTypeName()); geopoliticalTypeDTOs.add(geopoliticalTypeDTO);
		 * }
		 */
		countryDTO.setGeopoliticalType(geopoliticalTypeDTOs);
	}

}
