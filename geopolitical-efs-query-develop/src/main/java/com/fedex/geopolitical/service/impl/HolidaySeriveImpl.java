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
import com.fedex.geopolitical.dtoresponse.HolidayResponseDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.model.Holiday;
import com.fedex.geopolitical.repository.HolidayRepository;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;

@Service
public class HolidaySeriveImpl implements SearchService<String, HolidayResponseDTO, Holiday>{

	@Autowired
	HolidayRepository holidayRepository;

	@Override
	public QueryServiceResponseDTO search(String holidayName) throws ParseException {
		List<Holiday> holidays = new ArrayList<>();
		if (holidayName != null) {
			Optional<Holiday> geoplHday = holidayRepository.findByHolidayName(holidayName);
			if (geoplHday.isPresent()) {
				holidays.add(geoplHday.get());
			}
		} else {
			holidays = holidayRepository.findAll();
		}
		return prepareReponse(map(holidays));
	}

	@Override
	public QueryServiceResponseDTO prepareReponse(List<HolidayResponseDTO> holidayResponses) {
		MetaDTO metaResponse = new MetaDTO();
		QueryServiceResponseDTO queryServiceResponse = new QueryServiceResponseDTO();
		MessageDTO message = new MessageDTO();
		queryServiceResponse.setData(holidayResponses);
		if (CollectionUtils.isEmpty(holidayResponses)) {
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
	public List<HolidayResponseDTO> map(List<Holiday> holidays) throws ParseException {
		List<HolidayResponseDTO> holidayResponses = new ArrayList<>();

		if (!CollectionUtils.isEmpty(holidays)) {
			for (Holiday holiday : holidays) {
				HolidayResponseDTO holidayResponseDTO = new HolidayResponseDTO();
				holidayResponseDTO.setHolidayId(String.valueOf(holiday.getHolidayId()));
				holidayResponseDTO.setHolidayName(holiday.getHolidayName());
				holidayResponseDTO.setHolidayDateParameterText(holiday.getHolidayDateParamText());
				holidayResponses.add(holidayResponseDTO);
			}
		}
		return holidayResponses;
	}
}
