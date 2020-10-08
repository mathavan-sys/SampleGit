/*package com.fedex.geopolitical.service.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;
import com.fedex.geopolitical.dtoresponse.DependentCountryRelationshipResponseDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.model.Country;
import com.fedex.geopolitical.model.DependentCountryRelationship;
import com.fedex.geopolitical.repository.CountryRepository;
import com.fedex.geopolitical.repository.DependentCountryRelationshipRepository;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@Service
public class DependentCountryRelationshipSeriveImpl implements
		SearchService<BigInteger, DependentCountryRelationshipResponseDTO, DependentCountryRelationshipResponseDTO> {

	@Autowired
	DependentCountryRelationshipRepository dependentCountryRelationshipRepository;

	@Autowired
	CountryRepository countryRepository;

	@Override
	public QueryServiceResponseDTO search(BigInteger dependentCountryCd) throws ParseException {
		List<DependentCountryRelationshipResponseDTO> dependentCountryRelationship = new ArrayList<>();
		if (dependentCountryCd != null) {
			Optional<List<Country>> countryOpt = countryRepository.findByDependentCountryCd(dependentCountryCd);
			if (countryOpt.isPresent()) {
				for (Country country : countryOpt.get()) {
					if (country.getDependentRelationshipId() != null) {
						DependentCountryRelationshipResponseDTO dependentCountryRelationshipResponseDTO = new DependentCountryRelationshipResponseDTO();
						dependentCountryRelationshipResponseDTO.setGeopoliticalId(country.getId().getGeopoliticalId());
						Optional<DependentCountryRelationship> dependentCountryRelationshipOpt = dependentCountryRelationshipRepository
								.findById(country.getDependentRelationshipId());
						if (dependentCountryRelationshipOpt.isPresent()) {
							dependentCountryRelationshipResponseDTO.setDependentRelationshipDescription(
									dependentCountryRelationshipOpt.get().getDependentRelationshipDescription());
							dependentCountryRelationship.add(dependentCountryRelationshipResponseDTO);
						}
					}
				}
			}
		}
		return prepareReponse(map(dependentCountryRelationship));

	}

	@Override
	public QueryServiceResponseDTO prepareReponse(
			List<DependentCountryRelationshipResponseDTO> dependentCountryRelationshipResponseDTO) {
		MetaDTO metaResponse = new MetaDTO();
		QueryServiceResponseDTO queryServiceResponse = new QueryServiceResponseDTO();
		MessageDTO message = new MessageDTO();
		queryServiceResponse.setData(dependentCountryRelationshipResponseDTO);
		
		if (CollectionUtils.isEmpty(dependentCountryRelationshipResponseDTO)) {
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
	public List<DependentCountryRelationshipResponseDTO> map(
			List<DependentCountryRelationshipResponseDTO> dependentCountryRelationshipTypes) throws ParseException {
		return dependentCountryRelationshipTypes;
	}

}
*/
