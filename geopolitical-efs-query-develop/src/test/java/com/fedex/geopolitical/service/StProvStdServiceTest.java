package com.fedex.geopolitical.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dto.StProvStdDTO;
import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.dtoresponse.StProvStdResponseDTO;
import com.fedex.geopolitical.model.GeoplOrgStd;
import com.fedex.geopolitical.model.StProvStd;
import com.fedex.geopolitical.model.StProvStdPK;
import com.fedex.geopolitical.repository.StProvStdRepository;
import com.fedex.geopolitical.service.impl.StProvStdServiceImpl;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class StProvStdServiceTest {

	@Autowired
	ObjectMapper objectMapper;

	@InjectMocks
	private SearchService<StProvStdDTO, StProvStdResponseDTO, StProvStd> stProvStdService = new StProvStdServiceImpl();

	@Mock
	StProvStdRepository repository;

	@Test
	public void testGetStProvStd() throws ParseException {
		List<StProvStd> stProvStd = new ArrayList<>();
		Optional<StProvStd> stProvStdFirst = getStProvStd("1");
		Optional<StProvStd> stProvStdSecond = getStProvStd("2");
		stProvStd.add(stProvStdFirst.get());
		stProvStd.add(stProvStdSecond.get());

		SimpleDateFormat sdf = new SimpleDateFormat(GenericConstants.DATE_FORMAT);
		StProvStdDTO stProvStdDTO = new StProvStdDTO();
		stProvStdDTO.setCountryCode("US");
		stProvStdDTO.setStateProvinceCode("USA");
		stProvStdDTO.setOrganizationStandardCode("ISO");
		stProvStdDTO.setStateProvinceCode("USA");
		stProvStdDTO.setEffectiveDate(sdf.parse("2012-06-11"));
		stProvStdDTO.setExpirationDate(sdf.parse("2020-12-31"));
		
		MetaDTO metaResponse = new MetaDTO();
		MessageDTO message = new MessageDTO();
		metaResponse.setVersion("1.0.0");
		metaResponse.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaResponse.setStatusCode("200");
		message.setStatus(ResponseStatus.SUCCESS);
		message.setInternalMessage(GenericConstants.CONSTANT_INTERNAL_MSG);
		metaResponse.setMessage(message);

		QueryServiceResponseDTO queryServiceResponse = stProvStdService.search(stProvStdDTO);
		queryServiceResponse.setMeta(metaResponse);
		QueryServiceResponseDTO prepareResponse = stProvStdService.prepareReponse(stProvStdService.map(stProvStd));

		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);
		Assert.assertEquals(prepareResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);
	}

	private Optional<StProvStd> getStProvStd(String geopoliticalId) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(GenericConstants.DATE_FORMAT);
		StProvStd stProvStd = new StProvStd();
		StProvStdPK stdPk = new StProvStdPK();
		stdPk.setGeopoliticalId(geopoliticalId);
		stdPk.setOrgStdCd("ISO");
		stdPk.setEffectiveDate(sdf.parse("2012-06-06"));
		stProvStd.setStProvCd("USA");
		stProvStd.setStProvNm("USA");
		stProvStd.setExpirationDate(sdf.parse("2020-11-11"));
		stProvStd.setId(stdPk);
		
		GeoplOrgStd geoplOrgStd = new GeoplOrgStd();
		geoplOrgStd.setOrgStdCd("ISO");
		geoplOrgStd.setOrgStdNm("ISO 4217");
		stProvStd.setGeoplOrgStd(geoplOrgStd);
		return Optional.of(stProvStd);
	}

}
