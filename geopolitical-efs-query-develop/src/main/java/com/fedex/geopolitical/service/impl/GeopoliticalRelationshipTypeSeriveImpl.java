package com.fedex.geopolitical.service.impl;

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
import com.fedex.geopolitical.dtoresponse.GeopoliticalRelationshipTypeResponseDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.model.GeopoliticalRelationshipType;
import com.fedex.geopolitical.repository.GeopoliticalRelationshipTypeRepository;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@Service
public class GeopoliticalRelationshipTypeSeriveImpl implements SearchService<String, GeopoliticalRelationshipTypeResponseDTO, GeopoliticalRelationshipType> {

	@Autowired
	GeopoliticalRelationshipTypeRepository geopoliticalRelationshipTypeRepository;

	@Override
	public QueryServiceResponseDTO search(String geopoliticalRelationshipTypeCd) throws ParseException {
		List<GeopoliticalRelationshipType> geopoliticalRelationshipTypes = new ArrayList<>();
		if (geopoliticalRelationshipTypeCd != null) {
			Optional<GeopoliticalRelationshipType> geopoliticalRelationshipType = geopoliticalRelationshipTypeRepository
					.findByGeopoliticalRelationshipTypeCd(geopoliticalRelationshipTypeCd);
			if (geopoliticalRelationshipType.isPresent()) {
				geopoliticalRelationshipTypes.add(geopoliticalRelationshipType.get());
			}
		} else {
			geopoliticalRelationshipTypes = geopoliticalRelationshipTypeRepository.findAll();
		}
		return prepareReponse(map(geopoliticalRelationshipTypes));
	}

	@Override
	public QueryServiceResponseDTO prepareReponse(List<GeopoliticalRelationshipTypeResponseDTO> geopoliticalRelationshipTypeResponseDTO) {
		MetaDTO metaResponse = new MetaDTO();
		QueryServiceResponseDTO queryServiceResponse = new QueryServiceResponseDTO();
		MessageDTO message = new MessageDTO();
		queryServiceResponse.setData(geopoliticalRelationshipTypeResponseDTO);
		if (CollectionUtils.isEmpty(geopoliticalRelationshipTypeResponseDTO)) {
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
	public List<GeopoliticalRelationshipTypeResponseDTO> map(List<GeopoliticalRelationshipType> geopoliticalRelationshipTypes) throws ParseException {
		List<GeopoliticalRelationshipTypeResponseDTO> geopoliticalRelationshipTypeResponseDTOs = new ArrayList<>();
		if (!CollectionUtils.isEmpty(geopoliticalRelationshipTypes)) {
			for (GeopoliticalRelationshipType relationshipType : geopoliticalRelationshipTypes) {
				GeopoliticalRelationshipTypeResponseDTO geopoliticalRelationshipTypeResponseDTO = new GeopoliticalRelationshipTypeResponseDTO();
				geopoliticalRelationshipTypeResponseDTO
				.setGeopoliticalRelationshipTypeDescription(relationshipType.getAreaRelationshipTypeDescription());
				geopoliticalRelationshipTypeResponseDTO
				.setGeopoliticalRelationshipTypeCode(relationshipType.getGeopoliticalRelationshipTypeCd());
				geopoliticalRelationshipTypeResponseDTOs.add(geopoliticalRelationshipTypeResponseDTO);
			}
		}
		return geopoliticalRelationshipTypeResponseDTOs;
	}
}
