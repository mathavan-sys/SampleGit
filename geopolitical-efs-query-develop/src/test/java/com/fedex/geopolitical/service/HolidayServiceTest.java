package com.fedex.geopolitical.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.model.Holiday;
import com.fedex.geopolitical.repository.HolidayRepository;
import com.fedex.geopolitical.service.impl.HolidaySeriveImpl;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class HolidayServiceTest {

	@Autowired
	ObjectMapper objectMapper;

	@InjectMocks
	private HolidaySeriveImpl holidayService;

	@Mock
	HolidayRepository repository;

	@Test
	public void testGetHolidays() throws ParseException {
		List<Holiday> holiday = new ArrayList<>();
		Optional<Holiday> holidyaFirst = getHoliday("101");
		Optional<Holiday> holidaySecond = getHoliday("102");
		holiday.add(holidyaFirst.get());
		holiday.add(holidaySecond.get());
		
		MetaDTO metaResponse = new MetaDTO();
		MessageDTO message = new MessageDTO();
		metaResponse.setVersion("1.0.0");
		metaResponse.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaResponse.setStatusCode("200");
		message.setStatus(ResponseStatus.SUCCESS);
		message.setInternalMessage(GenericConstants.CONSTANT_INTERNAL_MSG);
		metaResponse.setMessage(message);

		 Mockito.when(repository.findByHolidayName("Halloween")).thenReturn(holidyaFirst);
		 
		QueryServiceResponseDTO queryServiceResponse = holidayService.search("Halloween");
		queryServiceResponse.setMeta(metaResponse);
		QueryServiceResponseDTO prepareResponse = holidayService.prepareReponse(holidayService.map(holiday));

		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);
		Assert.assertEquals(prepareResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);
		
	
	}

	private Optional<Holiday> getHoliday(String id) {
		Holiday holidayobj = new Holiday();
		holidayobj.setHolidayId(Long.parseLong(id));
		holidayobj.setHolidayName("Halloween");
		holidayobj.setHolidayDateParamText("Halloween");
		return Optional.of(holidayobj);
	}

}
