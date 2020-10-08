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
import com.fedex.geopolitical.dtoresponse.RefDayOfWeekResponseDTO;
import com.fedex.geopolitical.model.RefDayOfWeek;
import com.fedex.geopolitical.repository.RefDayOfWeekRepository;
import com.fedex.geopolitical.service.impl.RefDayOfWeekImpl;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class RefDayOfWeekServiceTest {

	@Autowired
	ObjectMapper objectMapper;

	@InjectMocks
	private SearchService<String, RefDayOfWeekResponseDTO, RefDayOfWeek> refDayOfWeekService = new RefDayOfWeekImpl();

	@Mock
	RefDayOfWeekRepository repository;

	@Test
	public void testGetDays() throws ParseException {
		List<RefDayOfWeek> refDayOfWeek = new ArrayList<>();
		Optional<RefDayOfWeek> dayOfWeekFirst = getRefDayOfWeek("1");
		Optional<RefDayOfWeek> dayOfWeekSecond = getRefDayOfWeek("2");
		refDayOfWeek.add(dayOfWeekFirst.get());
		refDayOfWeek.add(dayOfWeekSecond.get());
		
		MetaDTO metaResponse = new MetaDTO();
		MessageDTO message = new MessageDTO();
		metaResponse.setVersion("1.0.0");
		metaResponse.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaResponse.setStatusCode("200");
		message.setStatus(ResponseStatus.SUCCESS);
		message.setInternalMessage(GenericConstants.CONSTANT_INTERNAL_MSG);
		metaResponse.setMessage(message);

		Mockito.when(repository.findAll()).thenReturn(refDayOfWeek);

		QueryServiceResponseDTO queryServiceResponse = refDayOfWeekService.search("1");
		queryServiceResponse.setMeta(metaResponse);
		QueryServiceResponseDTO prepareResponse = refDayOfWeekService
				.prepareReponse(refDayOfWeekService.map(refDayOfWeek));

		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);
		Assert.assertEquals(prepareResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);
	}

	private Optional<RefDayOfWeek> getRefDayOfWeek(String week) {
		RefDayOfWeek dayOfWeek = new RefDayOfWeek();
		dayOfWeek.setDayOfWeekNumber(Long.parseLong(week));
		dayOfWeek.setDayOfWeekShortName("sun");
		dayOfWeek.setDayOfWeekFullName("Sunday");
		return Optional.of(dayOfWeek);
	}

}
