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
import com.fedex.geopolitical.model.RefScript;
import com.fedex.geopolitical.repository.RefScriptRepository;
import com.fedex.geopolitical.service.impl.RefScriptServiceImpl;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class RefScriptServiceTest {

	@Autowired
	ObjectMapper objectMapper;

	@InjectMocks
	private RefScriptServiceImpl refScriptService;

	@Mock
	RefScriptRepository repository;

	@Test
	public void testGetScript() throws ParseException {
		List<RefScript> refScript = new ArrayList<>();
		Optional<RefScript> scriptFirst = getRefScript("EN");
		Optional<RefScript> scriptSecond = getRefScript("HN");
		refScript.add(scriptFirst.get());
		refScript.add(scriptSecond.get());
		
		MetaDTO metaResponse = new MetaDTO();
		MessageDTO message = new MessageDTO();
		metaResponse.setVersion("1.0.0");
		metaResponse.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaResponse.setStatusCode("200");
		message.setStatus(ResponseStatus.SUCCESS);
		message.setInternalMessage(GenericConstants.CONSTANT_INTERNAL_MSG);
		metaResponse.setMessage(message);

		Mockito.when(repository.findByScrptCd("EN")).thenReturn(scriptFirst);

		QueryServiceResponseDTO queryServiceResponse = refScriptService.search("EN");
		queryServiceResponse.setMeta(metaResponse);
		QueryServiceResponseDTO prepareResponse = refScriptService.prepareReponse(refScriptService.map(refScript));

		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);
		Assert.assertEquals(prepareResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);

	}

	private Optional<RefScript> getRefScript(String script) {
		RefScript refscripts = new RefScript();
		refscripts.setScrptCd(script);
		refscripts.setScrptNm("1");
		refscripts.setScrptDesc("Hindi");
		return Optional.of(refscripts);
	}

}
