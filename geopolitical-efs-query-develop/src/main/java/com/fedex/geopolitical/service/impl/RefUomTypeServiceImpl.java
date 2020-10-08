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
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.dtoresponse.RefUomTypeResponseDTO;
import com.fedex.geopolitical.model.RefUomType;
import com.fedex.geopolitical.repository.RefUomTypeRepository;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;

import lombok.Setter;

@Service
@Setter
public class RefUomTypeServiceImpl implements SearchService<String, RefUomTypeResponseDTO, RefUomType> {
	
	@Autowired
	RefUomTypeRepository refUomTypeRepository;

	@Override
	public QueryServiceResponseDTO search(String uomTypeCd) throws ParseException {
		List<RefUomType> refUomTypes = new ArrayList<>();
		if (uomTypeCd != null) {
			Optional<RefUomType> refLanguage = refUomTypeRepository.findByUomTypeCd(uomTypeCd);
			if (refLanguage.isPresent()) {
				refUomTypes.add(refLanguage.get());
			}
		} else {
			refUomTypes = refUomTypeRepository.findAll();
		}
		return prepareReponse(map(refUomTypes));
	}

	@Override
	public QueryServiceResponseDTO prepareReponse(List<RefUomTypeResponseDTO> refUomTypeResponses) {
		MetaDTO metaResponse = new MetaDTO();
		QueryServiceResponseDTO queryServiceResponse = new QueryServiceResponseDTO();
		MessageDTO message = new MessageDTO();
		queryServiceResponse.setData(refUomTypeResponses);
		if (CollectionUtils.isEmpty(refUomTypeResponses)) {
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
	public List<RefUomTypeResponseDTO> map(List<RefUomType> refUomTypes) throws ParseException {
		List<RefUomTypeResponseDTO> refUomTypeResponses = new ArrayList<>();
		if (!CollectionUtils.isEmpty(refUomTypes)) {
			for (RefUomType refUomType : refUomTypes) {
				RefUomTypeResponseDTO refUomTypeResponseDTO = new RefUomTypeResponseDTO();
				refUomTypeResponseDTO.setUomTypeCode(refUomType.getUomTypeCd());
				refUomTypeResponseDTO.setUomTypeName(refUomType.getUomTypeNm());
				refUomTypeResponseDTO.setUomTypeDescription(refUomType.getUomTypeDesc());
				refUomTypeResponses.add(refUomTypeResponseDTO);
			}
		}
		return refUomTypeResponses;
	}
}
