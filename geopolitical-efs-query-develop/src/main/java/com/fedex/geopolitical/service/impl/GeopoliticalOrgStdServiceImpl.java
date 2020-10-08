package com.fedex.geopolitical.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;
import com.fedex.geopolitical.dtoresponse.GeoplOrgStdResponseDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.model.GeoplOrgStd;
import com.fedex.geopolitical.repository.GeoplOrgStdRepository;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@Component
public class GeopoliticalOrgStdServiceImpl implements SearchService<String, GeoplOrgStdResponseDTO, GeoplOrgStd> {

	@Autowired
	GeoplOrgStdRepository geoplOrgStdRepository;

	@Override
	public QueryServiceResponseDTO search(String orgStandardCode) throws ParseException {
		List<GeoplOrgStd> geoplOrgStds = new ArrayList<>();
		if (orgStandardCode != null) {
			Optional<GeoplOrgStd> geoplOrgStd = geoplOrgStdRepository.findByOrgStdCd(orgStandardCode);
			if (geoplOrgStd.isPresent()) {
				geoplOrgStds.add(geoplOrgStd.get());
			}
		} else {
			geoplOrgStds = geoplOrgStdRepository.findAll();
		}
		return prepareReponse(map(geoplOrgStds));

	}

	@Override
	public QueryServiceResponseDTO prepareReponse(List<GeoplOrgStdResponseDTO> geoplOrgStdResponseDTO) {
		MetaDTO metaResponse = new MetaDTO();
		QueryServiceResponseDTO queryServiceResponse = new QueryServiceResponseDTO();
		MessageDTO message = new MessageDTO();
		queryServiceResponse.setData(geoplOrgStdResponseDTO);
		if (CollectionUtils.isEmpty(geoplOrgStdResponseDTO)) {
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
	public List<GeoplOrgStdResponseDTO> map(List<GeoplOrgStd> geoplOrgStds) throws ParseException {
		List<GeoplOrgStdResponseDTO> geoplOrgStdResponses = new ArrayList<>();
		if (!CollectionUtils.isEmpty(geoplOrgStds)) {
			for (GeoplOrgStd geoplOrgStd : geoplOrgStds) {
				GeoplOrgStdResponseDTO geoplOrgStdResponseDTO = new GeoplOrgStdResponseDTO();
				geoplOrgStdResponseDTO.setOrganizationStandardCode(geoplOrgStd.getOrgStdCd());
				geoplOrgStdResponseDTO.setOrganizationStandardName(geoplOrgStd.getOrgStdNm());
				geoplOrgStdResponses.add(geoplOrgStdResponseDTO);
			}
		}
		return geoplOrgStdResponses;
	}
}
