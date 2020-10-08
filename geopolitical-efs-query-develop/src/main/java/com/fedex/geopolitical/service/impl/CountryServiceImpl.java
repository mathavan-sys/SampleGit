package com.fedex.geopolitical.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dto.AddressLabelDTO;
import com.fedex.geopolitical.dto.CountryDTO;
import com.fedex.geopolitical.dto.CountryOrganizationStandardDTO;
import com.fedex.geopolitical.dto.CountryOrgsDTO;
import com.fedex.geopolitical.dto.CountryServiceDTO;
import com.fedex.geopolitical.dto.CountryServiceMapperDTO;
import com.fedex.geopolitical.dto.CurrencyDTO;
import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.model.AddressLabel;
import com.fedex.geopolitical.model.CntryOrgStd;
import com.fedex.geopolitical.model.Country;
import com.fedex.geopolitical.model.Currency;
import com.fedex.geopolitical.repository.AddressLabelRepository;
import com.fedex.geopolitical.repository.CntryOrgStdRepository;
import com.fedex.geopolitical.repository.CountryRepository;
import com.fedex.geopolitical.repository.CurrencyRepository;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;

import lombok.Setter;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Setter
public class CountryServiceImpl implements SearchService<CountryServiceMapperDTO, CountryDTO, CountryServiceDTO> {

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	CntryOrgStdRepository cntryOrgStdRepository;

	@Autowired
	CurrencyRepository currencyRepository;

	@Autowired
	AddressLabelRepository addressLabelRepository;

	@Override
	public QueryServiceResponseDTO search(CountryServiceMapperDTO countryServiceMapperDTO) throws ParseException {
		List<Country> countries = new ArrayList<>();
		putDefaultEffectiveAndExpirationDate(countryServiceMapperDTO);
		if (countryServiceMapperDTO.getGeopoliticalId() != null) {
			countries = getCountriesByGeopoliticalIdAndDate(countryServiceMapperDTO.getGeopoliticalId(),
					countryServiceMapperDTO.getEffectiveDate(), countryServiceMapperDTO.getExpirationDate());
		} else if (countryServiceMapperDTO.getCountryCode() != null) {
			countries = getCountriesByCountryCd(countries, countryServiceMapperDTO);
		} else if ((countryServiceMapperDTO.getCountryShortName() != null)
				|| countryServiceMapperDTO.getOrganizationStandardCode() != null) {
			countries = getCountriesByFilters(countryServiceMapperDTO);
		} else {

			return getCountries(countries, countryServiceMapperDTO, true);
		}

		return getCountries(countries, countryServiceMapperDTO, false);
	}

	private QueryServiceResponseDTO getCountries(List<Country> countries,
			CountryServiceMapperDTO countryServiceMapperDTO, boolean isAllCountries) {
		List<CountryOrgsDTO> countryOrgsDTOs = new ArrayList<>();

		if (isAllCountries) {
			return getAllCountriesNew2(countryServiceMapperDTO); // Approach 3
																	// -- One
																	// query per
																	// table
		}

		if (!CollectionUtils.isEmpty(countries)) {
			for (Country country : countries) {

				CountryOrgsDTO countryOrgsDTO = new CountryOrgsDTO();
				mapCountryOrgs(countryOrgsDTO, country);
				mapCntryOrgStds(countryOrgsDTO, country, countryServiceMapperDTO);
				mapCurrenciesToCountryOrgs(countryOrgsDTO, country, countryServiceMapperDTO);
				mapAddressLabelstoCntryOrg(countryOrgsDTO, country, countryServiceMapperDTO);
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
				currencyDTO.setCurrencyCode(currency.getCurrencyCd());
				currencyDTO.setCurrencyNumericCode(currency.getId().getCurrencyNumberCd());
				currencyDTO.setMinorUnitCode(currency.getMinorUnitCd());
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

	private QueryServiceResponseDTO getAllCountriesNew2(CountryServiceMapperDTO countryServiceMapperDTO) {
		List<Country> countries = new ArrayList<>();
		Map<String, CountryOrgsDTO> countryOrgsDTOMap = new HashMap<String, CountryOrgsDTO>();
		countries = getAllCountries(countries, countryServiceMapperDTO);
		if (!CollectionUtils.isEmpty(countries)) {
			for (Country country : countries) {

				CountryOrgsDTO countryOrgsDTO = new CountryOrgsDTO();
				mapCountryOrgs(countryOrgsDTO, country);
				countryOrgsDTOMap.put(country.getId().getGeopoliticalId(), countryOrgsDTO);
			}
			// mapAllCntryOrgStds(countryServiceMapperDTO);
			HashMap<String, Set<CurrencyDTO>> hmAllCurrencies = mapALLCurrenciesToCountryOrgs(countryServiceMapperDTO);
			HashMap<String, Set<CountryOrganizationStandardDTO>> hmAllCntryStds = mapAllCntryOrgStds(
					countryServiceMapperDTO);
			HashMap<String, HashMap<String, List<AddressLabelDTO>>> hmAddressLabels = getAllAddressLabelsByGeoplID(
					countryServiceMapperDTO);

			for (String geoplId : countryOrgsDTOMap.keySet()) {
				CountryOrgsDTO countryOrgsDTO = countryOrgsDTOMap.get(geoplId);
				if (countryOrgsDTO != null) {
					countryOrgsDTO.setCurrencies(hmAllCurrencies.get(geoplId));
					countryOrgsDTO.setCountryOrganizationStandards(hmAllCntryStds.get(geoplId));
					countryOrgsDTO.setAddressLabels(hmAddressLabels.get(geoplId));
				}
			}
		}
		return prepareCountriesReponse(new ArrayList<CountryOrgsDTO>(countryOrgsDTOMap.values()));
	}

	private List<Country> getCountriesByCountryCd(List<Country> countries,
			CountryServiceMapperDTO countryServiceMapperDTO) {
		Optional<Country> country = countryRepository.findFirstByCountryCd(countryServiceMapperDTO.getCountryCode());
		if (country.isPresent()) {
			countries = getCountriesByGeopoliticalIdAndDate(country.get().getId().getGeopoliticalId(),
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
					predicates
							.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("cntryShtNm")),
									"%" + countryServiceMapperDTO.getCountryShortName().toUpperCase() + "%")));
				if (countryServiceMapperDTO.getOrganizationStandardCode() != null)
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("geoplOrgStd").get("orgStdCd")),
									"%" + countryServiceMapperDTO.getOrganizationStandardCode().toUpperCase() + "%")));
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

	private List<Country> getCountriesByGeopoliticalIdAndDate(String geopoliticalId, Date effectiveDate,
			Date expirationDate) {
		List<Country> countries = new ArrayList<>();
		Optional<List<Country>> countryOpt = countryRepository.findByGeopoliticalIdAndDate(geopoliticalId,
				effectiveDate, expirationDate);
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
		CommonUtility.populateSuccess_Status_StatusCd_TS(message, metaResponse);
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
		CommonUtility.populateSuccess_Status_StatusCd_TS(message, metaResponse);
		metaResponse.setVersion(GenericConstants.META_VERSION);
		metaResponse.setMessage(message);
		queryServiceResponse.setMeta(metaResponse);
		return queryServiceResponse;
	}

	@Override
	public List<CountryDTO> map(List<CountryServiceDTO> countryServiceDTOs) throws ParseException {
		List<CountryDTO> countryDTOs = new ArrayList<>();
		return countryDTOs;
	}

	private void mapCountryOrgs(CountryOrgsDTO countryOrgsDTO, Country country) {
		countryOrgsDTO.setGeopoliticalId(country.getId().getGeopoliticalId());
		countryOrgsDTO.setCountryNumericCode(country.getCountryNumberCd());
		countryOrgsDTO.setCountryCode(country.getCountryCd());
		countryOrgsDTO.setThreeCharacterCountryCode(country.getThreeCharCountryCd());
		countryOrgsDTO.setIndependentFlag(country.getIndependentFlag());
		countryOrgsDTO.setPostalFormatDescription(country.getPostalFormatDescription());
		countryOrgsDTO.setPostalFlag(country.getPostalFlag());
		countryOrgsDTO.setPostalLength(country.getPostalLengthNumber());
		countryOrgsDTO.setFirstWorkWeekDayName(country.getFirstWorkWeekDayName());
		countryOrgsDTO.setLastWorkWeekDayName(country.getLastWorkWeekDayName());
		countryOrgsDTO.setWeekendFirstDayName(country.getWeekendFirstDayName());
		countryOrgsDTO.setInternetDomainName(country.getInternetDomainName());
		countryOrgsDTO.setDependentRelationshipId(country.getDependentRelationshipId());
		countryOrgsDTO.setDependentCountryCode(country.getDependentCountryCd());
		countryOrgsDTO.setEffectiveDate(country.getId().getEffectiveDate());
		countryOrgsDTO.setExpirationDate(country.getExpirationDate());
		countryOrgsDTO.setInternationalDialingCode(country.getInternationalDialingCode());
		countryOrgsDTO.setLandPhoneMaximumLength(country.getLandPhoneMaximumLengthNumber() != null
				? country.getLandPhoneMaximumLengthNumber().longValue() : null);
		countryOrgsDTO.setLandPhoneMinimumLength(country.getLandPhoneMinimumLengthNumber() != null
				? country.getLandPhoneMinimumLengthNumber().longValue() : null);
		countryOrgsDTO.setMobilePhoneMaximumLength(country.getMobilePhoneMaximumLengthNumber() != null
				? country.getMobilePhoneMaximumLengthNumber().longValue() : null);
		countryOrgsDTO.setMobilePhoneMinimumLength(country.getMobilePhoneMinimumLengthNumber() != null
				? country.getMobilePhoneMinimumLengthNumber().longValue() : null);
		countryOrgsDTO.setPhoneNumberFormatPattern(country.getPhoneNumberPatternDescription());
	}

	private void mapCntryOrgStds(CountryOrgsDTO countryDTO, Country country,
			CountryServiceMapperDTO countryServiceMapperDTO) {
		Optional<List<CntryOrgStd>> cntryOrgStds = cntryOrgStdRepository.findByGeopoliticalId(
				country.getId().getGeopoliticalId(), countryServiceMapperDTO.getEffectiveDate(),
				countryServiceMapperDTO.getExpirationDate());
		if (cntryOrgStds.isPresent()) {
			Set<CountryOrganizationStandardDTO> CountryOrganizationStandardDTOs = new HashSet<>();
			for (CntryOrgStd cntryOrgStd : cntryOrgStds.get()) {
				CountryOrganizationStandardDTO countryOrganizationStandardDTO = new CountryOrganizationStandardDTO();
				countryOrganizationStandardDTO.setOrganizationStandardCode(cntryOrgStd.getId().getOrgStdCd());
				countryOrganizationStandardDTO.setOrganizationStandardName(cntryOrgStd.getGeoplOrgStd().getOrgStdNm());
				countryOrganizationStandardDTO.setCountryFullName(cntryOrgStd.getCntryFullNm());
				countryOrganizationStandardDTO.setCountryShortName(cntryOrgStd.getCntryShtNm());
				countryOrganizationStandardDTO.setEffectiveDate(cntryOrgStd.getId().getEffectiveDate());
				countryOrganizationStandardDTO.setExpirationDate(cntryOrgStd.getExpirationDate());
				CountryOrganizationStandardDTOs.add(countryOrganizationStandardDTO);
			}
			countryDTO.setCountryOrganizationStandards(CountryOrganizationStandardDTOs);
		}
	}

	private void mapAddressLabelstoCntryOrg(CountryOrgsDTO countryOrgsDTO, Country country,
			CountryServiceMapperDTO countryServiceMapperDTO) {
		Optional<List<AddressLabel>> addressList = addressLabelRepository.findByGeopoliticalId(
				String.valueOf(country.getId().getGeopoliticalId()), countryServiceMapperDTO.getEffectiveDate(),
				countryServiceMapperDTO.getExpirationDate());
		if (addressList.isPresent()) {
			HashMap<String, List<AddressLabelDTO>> addressDTOs = new HashMap<String, List<AddressLabelDTO>>();
			for (AddressLabel addressLabel : addressList.get()) {
				AddressLabelDTO addressLabelDTO = new AddressLabelDTO();
				addressLabelDTO.setAddressLineNumber(addressLabel.getId().getAddressLineNumber());
				addressLabelDTO.setBrandAddressLineDescription(addressLabel.getBrandAddressLineLabelDescription());
				addressLabelDTO.setApplicable(addressLabel.getApplicableFlag() != null
						&& addressLabel.getApplicableFlag().equalsIgnoreCase("1"));
				if (addressDTOs.get(addressLabel.getId().getLoclcode()) == null
						|| addressDTOs.get(addressLabel.getId().getLoclcode()).isEmpty()) {
					List<AddressLabelDTO> newList = new ArrayList<AddressLabelDTO>();
					newList.add(addressLabelDTO);
					addressDTOs.put(addressLabel.getId().getLoclcode(), newList);
				} else {
					List<AddressLabelDTO> reuseList = addressDTOs.get(addressLabel.getId().getLoclcode());
					reuseList.add(addressLabelDTO);
					addressDTOs.put(addressLabel.getId().getLoclcode(), reuseList);
				}

			}

			countryOrgsDTO.setAddressLabels(addressDTOs);
		}
	}

	private HashMap<String, HashMap<String, List<AddressLabelDTO>>> getAllAddressLabelsByGeoplID(
			CountryServiceMapperDTO countryServiceMapperDTO) {
		Optional<List<AddressLabel>> addressList = addressLabelRepository.findByAllAddressLabels(
				countryServiceMapperDTO.getEffectiveDate(), countryServiceMapperDTO.getExpirationDate());
		HashMap<String, HashMap<String, List<AddressLabelDTO>>> alMapByGeoplId = new HashMap<String, HashMap<String, List<AddressLabelDTO>>>();
		if (addressList.isPresent()) {
			HashMap<String, List<AddressLabelDTO>> addressDTOs = null;

			for (AddressLabel addressLabel : addressList.get()) {
				if (alMapByGeoplId.get(addressLabel.getId().getGeopoliticalId()) != null)
					addressDTOs = alMapByGeoplId.get(addressLabel.getId().getGeopoliticalId());
				else {
					addressDTOs = new HashMap<String, List<AddressLabelDTO>>();
					alMapByGeoplId.put(addressLabel.getId().getGeopoliticalId(), addressDTOs);
				}
				AddressLabelDTO addressLabelDTO = new AddressLabelDTO();
				addressLabelDTO.setAddressLineNumber(addressLabel.getId().getAddressLineNumber());
				addressLabelDTO.setBrandAddressLineDescription(addressLabel.getBrandAddressLineLabelDescription());
				addressLabelDTO.setApplicable(addressLabel.getApplicableFlag() != null
						&& addressLabel.getApplicableFlag().equalsIgnoreCase("1"));
				if (addressDTOs.get(addressLabel.getId().getLoclcode()) == null
						|| addressDTOs.get(addressLabel.getId().getLoclcode()).isEmpty()) {
					List<AddressLabelDTO> newList = new ArrayList<AddressLabelDTO>();
					newList.add(addressLabelDTO);
					addressDTOs.put(addressLabel.getId().getLoclcode(), newList);
				} else {
					List<AddressLabelDTO> reuseList = addressDTOs.get(addressLabel.getId().getLoclcode());
					reuseList.add(addressLabelDTO);
					addressDTOs.put(addressLabel.getId().getLoclcode(), reuseList);
				}

			}
		}
		return alMapByGeoplId;
	}

	private HashMap<String, Set<CurrencyDTO>> mapALLCurrenciesToCountryOrgs(
			CountryServiceMapperDTO countryServiceMapperDTO) {
		HashMap<String, Set<CurrencyDTO>> hmAllCurrencies = new HashMap<String, Set<CurrencyDTO>>();
		Optional<List<Currency>> currencies = currencyRepository.findByAllCurrencies(
				countryServiceMapperDTO.getEffectiveDate(), countryServiceMapperDTO.getExpirationDate());
		if (currencies.isPresent()) {
			Set<CurrencyDTO> currencyDTOs = null;
			for (Currency currency : currencies.get()) {
				if (hmAllCurrencies.get(currency.getId().getGeopoliticalId()) == null) {
					currencyDTOs = new HashSet<>();
					hmAllCurrencies.put(currency.getId().getGeopoliticalId(), currencyDTOs);
				} else
					currencyDTOs = hmAllCurrencies.get(currency.getId().getGeopoliticalId());
				CurrencyDTO currencyDTO = new CurrencyDTO();
				currencyDTO.setEffectiveDate(currency.getId().getEffectiveDate());
				currencyDTO.setExpirationDate(currency.getExpirationDate());
				currencyDTO.setCurrencyCode(currency.getCurrencyCd());
				currencyDTO.setCurrencyNumericCode(currency.getId().getCurrencyNumberCd());
				currencyDTO.setMinorUnitCode(currency.getMinorUnitCd());
				currencyDTO.setMoneyFormatDescription(currency.getMoneyFormatDescription());
				currencyDTOs.add(currencyDTO);
			}
		}
		return hmAllCurrencies;
	}

	private HashMap<String, Set<CountryOrganizationStandardDTO>> mapAllCntryOrgStds(
			CountryServiceMapperDTO countryServiceMapperDTO) {
		HashMap<String, Set<CountryOrganizationStandardDTO>> hmAllCntryOrgStds = new HashMap<String, Set<CountryOrganizationStandardDTO>>();
		Optional<List<CntryOrgStd>> cntryOrgStds = cntryOrgStdRepository.findByAllCntryOrgStd(
				countryServiceMapperDTO.getEffectiveDate(), countryServiceMapperDTO.getExpirationDate());
		if (cntryOrgStds.isPresent()) {
			Set<CountryOrganizationStandardDTO> countryOrganizationStandardDTOs = null;
			for (CntryOrgStd cntryOrgStd : cntryOrgStds.get()) {
				if (hmAllCntryOrgStds.get(cntryOrgStd.getId().getGeopoliticalId()) == null) {
					countryOrganizationStandardDTOs = new HashSet<>();
					hmAllCntryOrgStds.put(cntryOrgStd.getId().getGeopoliticalId(), countryOrganizationStandardDTOs);
				} else
					countryOrganizationStandardDTOs = hmAllCntryOrgStds.get(cntryOrgStd.getId().getGeopoliticalId());
				CountryOrganizationStandardDTO countryOrganizationStandardDTO = new CountryOrganizationStandardDTO();
				countryOrganizationStandardDTO.setOrganizationStandardCode(cntryOrgStd.getId().getOrgStdCd());
				countryOrganizationStandardDTO.setOrganizationStandardName(cntryOrgStd.getGeoplOrgStd().getOrgStdNm());
				countryOrganizationStandardDTO.setCountryFullName(cntryOrgStd.getCntryFullNm());
				countryOrganizationStandardDTO.setCountryShortName(cntryOrgStd.getCntryShtNm());
				countryOrganizationStandardDTO.setEffectiveDate(cntryOrgStd.getId().getEffectiveDate());
				countryOrganizationStandardDTO.setExpirationDate(cntryOrgStd.getExpirationDate());
				countryOrganizationStandardDTOs.add(countryOrganizationStandardDTO);
			}

		}
		return hmAllCntryOrgStds;
	}

}
