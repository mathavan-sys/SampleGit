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
import com.fedex.geopolitical.model.GeoplAffilType;
import com.fedex.geopolitical.repository.GeoplAffilTypeRepository;
import com.fedex.geopolitical.service.impl.GeopoliticalAffilTypeSeriveImpl;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;


@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class GeopllAffilTypeServiceTest {

	@Autowired
	ObjectMapper objectMapper;

	@InjectMocks
	private GeopoliticalAffilTypeSeriveImpl geopoliticalAffilTypeService;

	@Mock
	private GeoplAffilTypeRepository repository;


	@Test
	public void test() throws ParseException 
	{	
		List<GeoplAffilType> geoplAffilTypes = new ArrayList<>();
		Optional<GeoplAffilType> geoplAffilType = getGeoplAffilType("ST");
		Optional<GeoplAffilType> geoplAffilType2 = getGeoplAffilType("TS");

		geoplAffilTypes.add(geoplAffilType.get());
		geoplAffilTypes.add(geoplAffilType2.get());
		
		MetaDTO metaResponse = new MetaDTO();
		MessageDTO message = new MessageDTO();
		metaResponse.setVersion("1.0.0");
		metaResponse.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaResponse.setStatusCode("200");
		message.setStatus(ResponseStatus.SUCCESS);
		message.setInternalMessage(GenericConstants.CONSTANT_INTERNAL_MSG);
		metaResponse.setMessage(message);

		Mockito.when(repository.findByAffilTypeCode("ST")).thenReturn(geoplAffilType);

		Mockito.when(repository.findAll()).thenReturn(geoplAffilTypes);

		QueryServiceResponseDTO response = geopoliticalAffilTypeService.search("ST");
		response.setMeta(metaResponse);
		QueryServiceResponseDTO responsePrepare = geopoliticalAffilTypeService.prepareReponse(geopoliticalAffilTypeService.map(geoplAffilTypes));

		Assert.assertEquals(response.getMeta().getMessage().getStatus(),ResponseStatus.SUCCESS);
		Assert.assertEquals(responsePrepare.getMeta().getMessage().getStatus(),ResponseStatus.SUCCESS);
		
		response = geopoliticalAffilTypeService.search(null);
		responsePrepare = geopoliticalAffilTypeService.prepareReponse(geopoliticalAffilTypeService.map(geoplAffilTypes));

		Assert.assertEquals(response.getMeta().getMessage().getStatus(),ResponseStatus.SUCCESS);
		Assert.assertEquals(responsePrepare.getMeta().getMessage().getStatus(),ResponseStatus.SUCCESS);


	}

	private Optional<GeoplAffilType> getGeoplAffilType(String typeCode)
	{
		GeoplAffilType geoplAffilType = new GeoplAffilType();
		geoplAffilType.setAffilTypeCode(typeCode);
		geoplAffilType.setAffilTypeId(10l);
		geoplAffilType.setAffilTypeName(typeCode);

		return Optional.of(geoplAffilType);
	}

}
