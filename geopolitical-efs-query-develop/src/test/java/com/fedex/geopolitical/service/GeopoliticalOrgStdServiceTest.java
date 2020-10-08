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
import com.fedex.geopolitical.model.GeoplOrgStd;
import com.fedex.geopolitical.repository.GeoplOrgStdRepository;
import com.fedex.geopolitical.service.impl.GeopoliticalOrgStdServiceImpl;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class GeopoliticalOrgStdServiceTest {

	@Autowired
	ObjectMapper objectMapper;

	@InjectMocks
	private GeopoliticalOrgStdServiceImpl geoplOrgService;

	@Mock
	GeoplOrgStdRepository repository;

	@Test
	public void testGetOrgStd() throws ParseException {

		List<GeoplOrgStd> geoplOrg = new ArrayList<>();
		Optional<GeoplOrgStd> orgSdFirst = getGeoplOrg("IND");
		Optional<GeoplOrgStd> orgStdSecond = getGeoplOrg("USA");
		geoplOrg.add(orgSdFirst.get());
		geoplOrg.add(orgStdSecond.get());
		
		MetaDTO metaResponse = new MetaDTO();
		MessageDTO message = new MessageDTO();
		metaResponse.setVersion("1.0.0");
		metaResponse.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaResponse.setStatusCode("200");
		message.setStatus(ResponseStatus.SUCCESS);
		message.setInternalMessage(GenericConstants.CONSTANT_INTERNAL_MSG);
		metaResponse.setMessage(message);

		Mockito.when(repository.findByOrgStdCd("IND")).thenReturn(orgStdSecond);

		Mockito.when(repository.findAll()).thenReturn(geoplOrg);

		QueryServiceResponseDTO queryServiceResponse = geoplOrgService.search("IND");
		queryServiceResponse.setMeta(metaResponse);
		QueryServiceResponseDTO prepareResponse = geoplOrgService.prepareReponse(geoplOrgService.map(geoplOrg));
		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);
		Assert.assertEquals(prepareResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);

		queryServiceResponse = geoplOrgService.search(null);
		prepareResponse = geoplOrgService.prepareReponse(geoplOrgService.map(geoplOrg));
		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);
		Assert.assertEquals(prepareResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);

	}

	private Optional<GeoplOrgStd> getGeoplOrg(String orgCd) {
		GeoplOrgStd geoplOrgStd = new GeoplOrgStd();
		geoplOrgStd.setOrgStdCd(orgCd);
		geoplOrgStd.setOrgStdNm("India");
		return Optional.of(geoplOrgStd);
	}
}
