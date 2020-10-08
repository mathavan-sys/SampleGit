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
import org.springframework.test.context.ActiveProfiles;

import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dto.GeopoliticalRelationshipTypeDTO;
import com.fedex.geopolitical.dto.User;
import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.model.GeopoliticalRelationshipType;
import com.fedex.geopolitical.repository.GeopoliticalRelationshipTypeRepository;
import com.fedex.geopolitical.service.impl.GeopoliticalRelationshipTypeSeriveImpl;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class GeopoliticalRelationshipTypeServiceTest {

	@InjectMocks
	private GeopoliticalRelationshipTypeSeriveImpl geopoliticalRelationshipTypeService;

	@Mock
	private GeopoliticalRelationshipTypeRepository geopoliticalRelationshipTypeRepository;

	@Test
	public void testUpdate() throws ParseException {
		GeopoliticalRelationshipTypeDTO geopoliticalRelationshipTypeRequestDTO = new GeopoliticalRelationshipTypeDTO();
		geopoliticalRelationshipTypeRequestDTO.setGeopoliticalRelationshipTypeCode("10");
		geopoliticalRelationshipTypeRequestDTO.setAreaRelationshipTypeDescription("ThanksGivingDay");
		geopoliticalRelationshipTypeRequestDTO.setUser(new User("3766271"));
		Optional<GeopoliticalRelationshipType> geopoliticalType = getGeopoliticalRelationshipType("Christmas");
		Optional<GeopoliticalRelationshipType> resultGeopoliticalType = getGeopoliticalRelationshipType(
				"ThanksGivingDay");
		
		MetaDTO metaResponse = new MetaDTO();
		MessageDTO message = new MessageDTO();
		metaResponse.setVersion("1.0.0");
		metaResponse.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaResponse.setStatusCode("200");
		message.setStatus(ResponseStatus.SUCCESS);
		message.setInternalMessage(GenericConstants.CONSTANT_INTERNAL_MSG);
		metaResponse.setMessage(message);

		Mockito.when(geopoliticalRelationshipTypeRepository.findByGeopoliticalRelationshipTypeCd("10"))
				.thenReturn(geopoliticalType);

		List<GeopoliticalRelationshipType> allResults = new ArrayList<>();
		allResults.add(resultGeopoliticalType.get());
		
		QueryServiceResponseDTO resp = geopoliticalRelationshipTypeService.search("10");
		resp.setMeta(metaResponse);
		Assert.assertEquals(ResponseStatus.SUCCESS, resp.getMeta().getMessage().getStatus());

	}

	private Optional<GeopoliticalRelationshipType> getGeopoliticalRelationshipType(
			String geopoliticalRelationTypeName) {
		GeopoliticalRelationshipType geopoliticalRelationshipType = new GeopoliticalRelationshipType();
		geopoliticalRelationshipType.setGeopoliticalRelationshipTypeCd("10");
		geopoliticalRelationshipType.setAreaRelationshipTypeDescription(geopoliticalRelationTypeName);
		geopoliticalRelationshipType.setCreatedByUserId("3766271");
		geopoliticalRelationshipType.setLastUpdatedByNm("3766271");
		return Optional.of(geopoliticalRelationshipType);
	}

}
