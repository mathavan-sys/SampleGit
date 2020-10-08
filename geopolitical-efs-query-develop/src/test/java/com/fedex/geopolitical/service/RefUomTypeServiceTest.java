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
import com.fedex.geopolitical.model.RefUomType;
import com.fedex.geopolitical.repository.RefUomTypeRepository;
import com.fedex.geopolitical.service.impl.RefUomTypeServiceImpl;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class RefUomTypeServiceTest {

	@Autowired
	ObjectMapper objectMapper;

	@InjectMocks
	private RefUomTypeServiceImpl refUomTypeService;

	@Mock
	RefUomTypeRepository repository;

	@Test
	public void testGetUomType() throws ParseException {

		refUomTypeService.setRefUomTypeRepository(repository);
		
		List<RefUomType> refUomType = new ArrayList<>();
		Optional<RefUomType> uomTypeFirst = getRefUomType("kg");
		Optional<RefUomType> uomTypeSecond = getRefUomType("gm");
		refUomType.add(uomTypeFirst.get());
		
		MetaDTO metaResponse = new MetaDTO();
		MessageDTO message = new MessageDTO();
		metaResponse.setVersion("1.0.0");
		metaResponse.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaResponse.setStatusCode("200");
		message.setStatus(ResponseStatus.SUCCESS);
		message.setInternalMessage(GenericConstants.CONSTANT_INTERNAL_MSG);
		metaResponse.setMessage(message);

		Mockito.when(repository.findByUomTypeCd("kg")).thenReturn(uomTypeSecond);

		Mockito.when(repository.findAll()).thenReturn(refUomType);

		QueryServiceResponseDTO queryServiceResponse = refUomTypeService.search("kg");
		queryServiceResponse.setMeta(metaResponse);
		QueryServiceResponseDTO prepareResponse = refUomTypeService.prepareReponse(refUomTypeService.map(refUomType));

		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);
		Assert.assertEquals(prepareResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);

		queryServiceResponse = refUomTypeService.search(null);

		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);
	}

	private Optional<RefUomType> getRefUomType(String uomTypeCd) {
		RefUomType UomType = new RefUomType();
		UomType.setUomTypeCd(uomTypeCd);
		UomType.setUomTypeNm("Kilogram");
		UomType.setUomTypeDesc("Kilogram");
		return Optional.of(UomType);
	}

}
