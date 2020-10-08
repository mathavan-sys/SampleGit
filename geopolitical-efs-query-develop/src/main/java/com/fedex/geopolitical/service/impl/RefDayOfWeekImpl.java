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
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.dtoresponse.RefDayOfWeekResponseDTO;
import com.fedex.geopolitical.model.RefDayOfWeek;
import com.fedex.geopolitical.repository.RefDayOfWeekRepository;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;

@Service
public class RefDayOfWeekImpl implements SearchService<String, RefDayOfWeekResponseDTO, RefDayOfWeek> {

	@Autowired
	RefDayOfWeekRepository dowRepository;

	@Override
	public QueryServiceResponseDTO search(String refDay) throws ParseException {
		List<RefDayOfWeek> refDayOfWeeks = dowRepository.findAll();
		return prepareReponse(map(refDayOfWeeks));
	}

	@Override
	public QueryServiceResponseDTO prepareReponse(List<RefDayOfWeekResponseDTO> refDayOfWeekResponses) {
		MetaDTO metaResponse = new MetaDTO();
		QueryServiceResponseDTO queryServiceResponse = new QueryServiceResponseDTO();
		MessageDTO message = new MessageDTO();
		queryServiceResponse.setData(refDayOfWeekResponses);
		if (CollectionUtils.isEmpty(refDayOfWeekResponses)) {
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
	public List<RefDayOfWeekResponseDTO> map(List<RefDayOfWeek> refDayOfWeeks) throws ParseException {

		List<RefDayOfWeekResponseDTO> refDayOfWeekResponses = new ArrayList<>();

		if (!CollectionUtils.isEmpty(refDayOfWeeks)) {
			for (RefDayOfWeek refDayOfWeek : refDayOfWeeks) {
				RefDayOfWeekResponseDTO refDayOfWeekResponseDTO = new RefDayOfWeekResponseDTO();
				refDayOfWeekResponseDTO.setDayOfWeekNumber(String.valueOf(refDayOfWeek.getDayOfWeekNumber()));
				refDayOfWeekResponseDTO.setDayOfWeekFullName(refDayOfWeek.getDayOfWeekFullName());
				refDayOfWeekResponseDTO.setDayOfWeekShortName(refDayOfWeek.getDayOfWeekShortName());
				refDayOfWeekResponses.add(refDayOfWeekResponseDTO);
			}
		}
		return refDayOfWeekResponses;

	}

}