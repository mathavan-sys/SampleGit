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
import com.fedex.geopolitical.dtoresponse.RefMonthOfYearResponseDTO;
import com.fedex.geopolitical.model.RefMonthOfYear;
import com.fedex.geopolitical.repository.RefMonthOfYearRepository;
import com.fedex.geopolitical.service.impl.RefMonthOfYearImpl;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class RefMonthOfYearServiceTest {

	@Autowired
	ObjectMapper objectMapper;

	@InjectMocks
	private SearchService<String, RefMonthOfYearResponseDTO, RefMonthOfYear> refMonthOfYearService = new RefMonthOfYearImpl();

	@Mock
	RefMonthOfYearRepository repository;

	@Test
	public void testGetMonths() throws ParseException {

		List<RefMonthOfYear> refMonthOfYear = new ArrayList<>();
		Optional<RefMonthOfYear> monthOfYearFirst = getRefMonthOfYear("1");
		Optional<RefMonthOfYear> monthOfYearSecond = getRefMonthOfYear("2");
		refMonthOfYear.add(monthOfYearFirst.get());
		refMonthOfYear.add(monthOfYearSecond.get());
		
		MetaDTO metaResponse = new MetaDTO();
		MessageDTO message = new MessageDTO();
		metaResponse.setVersion("1.0.0");
		metaResponse.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaResponse.setStatusCode("200");
		message.setStatus(ResponseStatus.SUCCESS);
		message.setInternalMessage(GenericConstants.CONSTANT_INTERNAL_MSG);
		metaResponse.setMessage(message);

		Mockito.when(repository.findAll()).thenReturn(refMonthOfYear);

		QueryServiceResponseDTO queryServiceResponse = refMonthOfYearService.search("1");
		queryServiceResponse.setMeta(metaResponse);
		QueryServiceResponseDTO prepareResponse = refMonthOfYearService
				.prepareReponse(refMonthOfYearService.map(refMonthOfYear));

		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);
		Assert.assertEquals(prepareResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);
	}

	private Optional<RefMonthOfYear> getRefMonthOfYear(String month) {
		RefMonthOfYear monthOfYear = new RefMonthOfYear();
		monthOfYear.setMonthOfYearNumber(Long.parseLong(month));
		monthOfYear.setMonthOfYearShortName("Jan");
		return Optional.of(monthOfYear);
	}

}
