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
import com.fedex.geopolitical.model.GeopoliticalType;
import com.fedex.geopolitical.repository.GeopoliticalTypeRepository;
import com.fedex.geopolitical.service.impl.GeopoliticalTypeSeriveImpl;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class GeopoliticalTypeServiceTest {

	@Autowired
	ObjectMapper objectMapper;

	@InjectMocks
	private GeopoliticalTypeSeriveImpl geopoliticalService;

	@Mock
	GeopoliticalTypeRepository repository;

	@Test
	public void testGeoPolitical() throws ParseException {
		List<GeopoliticalType> geopolitical = new ArrayList<>();
		Optional<GeopoliticalType> geoFirst = getGeoPolitical("335567");
		Optional<GeopoliticalType> geoSecond = getGeoPolitical("335568");
		geopolitical.add(geoFirst.get());
		geopolitical.add(geoSecond.get());
		
		MetaDTO metaResponse = new MetaDTO();
		MessageDTO message = new MessageDTO();
		metaResponse.setVersion("1.0.0");
		metaResponse.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaResponse.setStatusCode("200");
		message.setStatus(ResponseStatus.SUCCESS);
		message.setInternalMessage(GenericConstants.CONSTANT_INTERNAL_MSG);
		metaResponse.setMessage(message);

		Mockito.when(repository.findByGeopoliticalTypeName("335567")).thenReturn(geoFirst);

		Mockito.when(repository.findAll()).thenReturn(geopolitical);

		QueryServiceResponseDTO queryServiceResponse = geopoliticalService.search("335567");
		QueryServiceResponseDTO prepareResponse = geopoliticalService
				.prepareReponse(geopoliticalService.map(geopolitical));
		queryServiceResponse.setMeta(metaResponse);

		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);
		Assert.assertEquals(prepareResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);
		
		queryServiceResponse = geopoliticalService.search(null);
		prepareResponse = geopoliticalService
				.prepareReponse(geopoliticalService.map(geopolitical));

		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);
		Assert.assertEquals(prepareResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);
	}

	private Optional<GeopoliticalType> getGeoPolitical(String id) {
		GeopoliticalType geopoliticalType = new GeopoliticalType();
		geopoliticalType.setGeopoliticalTypeId(Long.parseLong(id));
		geopoliticalType.setGeopoliticalTypeName("366713");
		return Optional.of(geopoliticalType);
	}

}
