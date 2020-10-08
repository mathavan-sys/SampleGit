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
import com.fedex.geopolitical.dtoresponse.RefMonthOfYearResponseDTO;
import com.fedex.geopolitical.model.RefMonthOfYear;
import com.fedex.geopolitical.repository.RefMonthOfYearRepository;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@Service
public class RefMonthOfYearImpl implements SearchService<String, RefMonthOfYearResponseDTO, RefMonthOfYear> {

	@Autowired
	RefMonthOfYearRepository moyRepository;

	@Override
	public QueryServiceResponseDTO search(String refMonth) throws ParseException {
		List<RefMonthOfYear> refMonthOfYear = moyRepository.findAll();
		return prepareReponse(map(refMonthOfYear));
	}

	@Override
	public QueryServiceResponseDTO prepareReponse(List<RefMonthOfYearResponseDTO> refMonthOfYearResponses) {
		QueryServiceResponseDTO queryServiceResponse = new QueryServiceResponseDTO();
		MetaDTO metaResponse = new MetaDTO();
		MessageDTO message = new MessageDTO();
		if (CollectionUtils.isEmpty(refMonthOfYearResponses)) {
			message.setInternalMessage(GenericConstants.NO_RECORDS);
		} else {
			message.setInternalMessage(GenericConstants.CONSTANT_INTERNAL_MSG);
		}
		CommonUtility.populateSuccess_Status_StatusCd_TS(message,metaResponse);
		metaResponse.setVersion(GenericConstants.META_VERSION);
		metaResponse.setMessage(message);
		queryServiceResponse.setMeta(metaResponse);
		queryServiceResponse.setData(refMonthOfYearResponses);
		return queryServiceResponse;
	}

	@Override
	public List<RefMonthOfYearResponseDTO> map(List<RefMonthOfYear> refMonthOfYear) throws ParseException {
		List<RefMonthOfYearResponseDTO> refMonthOfYearResponseDTO = new ArrayList<>();
		if (!CollectionUtils.isEmpty(refMonthOfYear)) {
			for (RefMonthOfYear monthOfYear : refMonthOfYear) {
				RefMonthOfYearResponseDTO responseDTO = new RefMonthOfYearResponseDTO();
				responseDTO.setMonthOfYearNumber(String.valueOf(monthOfYear.getMonthOfYearNumber()));
				responseDTO.setMonthOfYearShortName(monthOfYear.getMonthOfYearShortName());
				refMonthOfYearResponseDTO.add(responseDTO);
			}
		}
		return refMonthOfYearResponseDTO;
	}

}
