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
import com.fedex.geopolitical.dtoresponse.GeopoliticalTypeResponseDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.model.GeopoliticalType;
import com.fedex.geopolitical.repository.GeopoliticalTypeRepository;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;

@Service
public class GeopoliticalTypeSeriveImpl implements SearchService<String, GeopoliticalTypeResponseDTO, GeopoliticalType> {

	@Autowired
	GeopoliticalTypeRepository geopoliticalTypeRepository;

	@Override
	public QueryServiceResponseDTO search(String geoPoliticalTypeName) throws ParseException {
		List<GeopoliticalType> geopoliticalTypes = new ArrayList<>();
		if (geoPoliticalTypeName != null) {
			Optional<GeopoliticalType> geopoliticalType = geopoliticalTypeRepository.findByGeopoliticalTypeName(geoPoliticalTypeName);
			if (geopoliticalType.isPresent()) {
				geopoliticalTypes.add(geopoliticalType.get());
			}
		} else {
			geopoliticalTypes = geopoliticalTypeRepository.findAll();
		}
		return prepareReponse(map(geopoliticalTypes));
	}

	@Override
	public QueryServiceResponseDTO prepareReponse(List<GeopoliticalTypeResponseDTO> geopoliticalTypeResponseDTO) {
		MetaDTO metaResponse = new MetaDTO();
		QueryServiceResponseDTO queryServiceResponse = new QueryServiceResponseDTO();
		MessageDTO message = new MessageDTO();
		queryServiceResponse.setData(geopoliticalTypeResponseDTO);
		if (CollectionUtils.isEmpty(geopoliticalTypeResponseDTO)) {
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
	public List<GeopoliticalTypeResponseDTO> map(List<GeopoliticalType> geopoliticalTypes) throws ParseException {
		List<GeopoliticalTypeResponseDTO> geopoliticalTypeResponseDTOs = new ArrayList<>();
		if (!CollectionUtils.isEmpty(geopoliticalTypes)) {
			for (GeopoliticalType geopoliticalType : geopoliticalTypes) {
				GeopoliticalTypeResponseDTO geopoliticalTypeResponseDTO = new GeopoliticalTypeResponseDTO();
				geopoliticalTypeResponseDTO.setGeopoliticalTypeId(String.valueOf(geopoliticalType.getGeopoliticalTypeId()));
				geopoliticalTypeResponseDTO.setGeopoliticalTypeName(geopoliticalType.getGeopoliticalTypeName());
				geopoliticalTypeResponseDTOs.add(geopoliticalTypeResponseDTO);
			}
		}
		return geopoliticalTypeResponseDTOs;
	}
}


