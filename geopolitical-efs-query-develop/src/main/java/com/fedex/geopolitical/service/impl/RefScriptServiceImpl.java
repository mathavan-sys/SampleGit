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
import com.fedex.geopolitical.dtoresponse.RefScriptResponseDTO;
import com.fedex.geopolitical.model.RefScript;
import com.fedex.geopolitical.repository.RefScriptRepository;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;

@Service
public class RefScriptServiceImpl implements SearchService<String, RefScriptResponseDTO, RefScript> {
	@Autowired
	RefScriptRepository refScriptRepository;

	@Override
	public QueryServiceResponseDTO search(String scrptCd) throws ParseException {
		List<RefScript> refScripts = new ArrayList<>();
		if (scrptCd != null) {
			Optional<RefScript> refScript = refScriptRepository.findByScrptCd(scrptCd);
			if (refScript.isPresent()) {
				refScripts.add(refScript.get());
			}
		} else {
			refScripts = refScriptRepository.findAll();
		}
		return prepareReponse(map(refScripts));
	}

	@Override
	public QueryServiceResponseDTO prepareReponse(List<RefScriptResponseDTO> refScriptResponses) {
		MetaDTO metaResponse = new MetaDTO();
		QueryServiceResponseDTO queryServiceResponse = new QueryServiceResponseDTO();
		MessageDTO message = new MessageDTO();
		queryServiceResponse.setData(refScriptResponses);
		if (CollectionUtils.isEmpty(refScriptResponses)) {
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
	public List<RefScriptResponseDTO> map(List<RefScript> refScripts) throws ParseException {
		List<RefScriptResponseDTO> refScriptResponses = new ArrayList<>();
		if (!CollectionUtils.isEmpty(refScripts)) {
			for (RefScript refScript : refScripts) {
				RefScriptResponseDTO refScriptResponseDTO = new RefScriptResponseDTO();
				refScriptResponseDTO.setScriptCode(refScript.getScrptCd());
				refScriptResponseDTO.setScriptName(refScript.getScrptNm());
				refScriptResponseDTO.setScriptDescription(refScript.getScrptDesc());
				refScriptResponses.add(refScriptResponseDTO);
			}
		}
		return refScriptResponses;
	}
	

}
