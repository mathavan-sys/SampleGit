package com.fedex.geopolitical.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;
import com.fedex.geopolitical.dtoresponse.DependentCountryRelationshipTypeResponseDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.model.DependentCountryRelationship;
import com.fedex.geopolitical.repository.DependentCountryRelationshipRepository;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@Service
public class DependentCountryRelationshipTypeSeriveImpl implements SearchService<String, DependentCountryRelationshipTypeResponseDTO, DependentCountryRelationship> {

	@Autowired
	DependentCountryRelationshipRepository dependentCountryRelationshipRepository;

	@Override
	public QueryServiceResponseDTO search(String dependentCountryCd) throws ParseException {
		List<DependentCountryRelationship> dependentCountryRelationship = dependentCountryRelationshipRepository.findAll();
		return prepareReponse(map(dependentCountryRelationship));
	}

	@Override
	public QueryServiceResponseDTO prepareReponse(List<DependentCountryRelationshipTypeResponseDTO> dependentCountryRelationshipTypeResponses) {
		MetaDTO metaResponse = new MetaDTO();
		QueryServiceResponseDTO queryServiceResponse = new QueryServiceResponseDTO();
		MessageDTO message = new MessageDTO();
		queryServiceResponse.setData(dependentCountryRelationshipTypeResponses);
		if (CollectionUtils.isEmpty(dependentCountryRelationshipTypeResponses)) {
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
	public List<DependentCountryRelationshipTypeResponseDTO> map(List<DependentCountryRelationship> dependentCountryRelationshipTypes) throws ParseException {
		List<DependentCountryRelationshipTypeResponseDTO> dependentCountryRelationshipResponses = new ArrayList<>();

		if (!CollectionUtils.isEmpty(dependentCountryRelationshipTypes)) {
			for (DependentCountryRelationship dependentCountryRelationshipType : dependentCountryRelationshipTypes) {
				DependentCountryRelationshipTypeResponseDTO dependentCountryRelationshipResponseDTO = new DependentCountryRelationshipTypeResponseDTO();
				dependentCountryRelationshipResponseDTO.setDependentRelationshipTypeId(dependentCountryRelationshipType.getDependentRelationshipId());
				dependentCountryRelationshipResponseDTO.setDependentRelationshipTypeDescription(dependentCountryRelationshipType.getDependentRelationshipDescription());
				dependentCountryRelationshipResponses.add(dependentCountryRelationshipResponseDTO);
			}
		}
		return dependentCountryRelationshipResponses;
	}

}
