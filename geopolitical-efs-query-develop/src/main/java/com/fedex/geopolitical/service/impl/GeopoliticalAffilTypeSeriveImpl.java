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
import com.fedex.geopolitical.dtoresponse.GeoplAffilTypeResponseDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.model.GeoplAffilType;
import com.fedex.geopolitical.repository.GeoplAffilTypeRepository;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@Service
public class GeopoliticalAffilTypeSeriveImpl implements SearchService<String, GeoplAffilTypeResponseDTO, GeoplAffilType> {

	@Autowired
	GeoplAffilTypeRepository affiltypeRepository;

	@Override
	public QueryServiceResponseDTO search(String affilTypeCode) throws ParseException {
		List<GeoplAffilType> geoplAffilTypes = new ArrayList<>();
		if (affilTypeCode != null) {
			Optional<GeoplAffilType> geoplAffilType = affiltypeRepository.findByAffilTypeCode(affilTypeCode);
			if (geoplAffilType.isPresent()) {
				geoplAffilTypes.add(geoplAffilType.get());
			}
		} else {
			geoplAffilTypes = affiltypeRepository.findAll();
		}
		return prepareReponse(map(geoplAffilTypes));
	}

	@Override
	public QueryServiceResponseDTO prepareReponse(List<GeoplAffilTypeResponseDTO> geoplAffilTypeResponses) {
		MetaDTO metaResponse = new MetaDTO();
		QueryServiceResponseDTO queryServiceResponse = new QueryServiceResponseDTO();
		MessageDTO message = new MessageDTO();
		queryServiceResponse.setData(geoplAffilTypeResponses);
		if (CollectionUtils.isEmpty(geoplAffilTypeResponses)) {
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
	public List<GeoplAffilTypeResponseDTO> map(List<GeoplAffilType> geoplAffilTypes) throws ParseException {
		List<GeoplAffilTypeResponseDTO> geoplAffilTypeResponses = new ArrayList<>();

		if (!CollectionUtils.isEmpty(geoplAffilTypes)) {
			for (GeoplAffilType geoplAffilType : geoplAffilTypes) {
				GeoplAffilTypeResponseDTO geoplAffilTypeResponseDTO = new GeoplAffilTypeResponseDTO();
				geoplAffilTypeResponseDTO.setAffiliationTypeId(String.valueOf(geoplAffilType.getAffilTypeId()));
				geoplAffilTypeResponseDTO.setAffiliationTypeCode(geoplAffilType.getAffilTypeCode());
				geoplAffilTypeResponseDTO.setAffiliationTypeName(geoplAffilType.getAffilTypeName());
				geoplAffilTypeResponses.add(geoplAffilTypeResponseDTO);
			}
		}
		return geoplAffilTypeResponses;
	}

}
