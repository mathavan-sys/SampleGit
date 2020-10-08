package com.fedex.geopolitical.service;

import static org.mockito.Mockito.when;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dto.AddressLabelDTO;
import com.fedex.geopolitical.dto.AddressServiceMapperDTO;
import com.fedex.geopolitical.dto.CountryServiceMapperDTO;
import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.model.AddressLabel;
import com.fedex.geopolitical.model.AddressLabelPK;
import com.fedex.geopolitical.repository.AddressLabelRepository;
import com.fedex.geopolitical.service.impl.AddressLabelServiceImpl;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.CommonUtilityTest;
import com.fedex.geopolitical.utility.ResponseStatus;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class AddressLabelServiceTest {

	@Autowired
	ObjectMapper objectMapper;

	@Mock
	AddressLabelRepository addressLabelRepository;

	@InjectMocks
	private AddressLabelServiceImpl addressLabelService;

	@Test
	public void testgetAllAddressLabel() throws ParseException {

		String id = "1";
		Date effectiveDate = Date.valueOf("2018-06-12");
		Date expirationDate = Date.valueOf("9999-12-31");
		String localeCd = "EN";

		addressLabelService.setAddressRepository(addressLabelRepository);

		when(addressLabelRepository.findByGeopoliticalId(String.valueOf(id), effectiveDate, expirationDate))
				.thenReturn(getAddressLabel(id));
		when(addressLabelRepository.findByGeopoliticalIdAndLocale(String.valueOf(id), localeCd, effectiveDate,
				expirationDate)).thenReturn(getAddressLabel(id));
		when(addressLabelRepository.findAllByDate(effectiveDate, expirationDate)).thenReturn(getAddressLabel(id));

		AddressServiceMapperDTO addressServiceMapperDTO = new AddressServiceMapperDTO();
		addressServiceMapperDTO.setLocaleCode(localeCd);
		addressServiceMapperDTO.setGeopoliticalId(String.valueOf(id));
		addressServiceMapperDTO.setEffectiveDate(effectiveDate);
		addressServiceMapperDTO.setExpirationDate(expirationDate);

		CountryServiceMapperDTO countryServiceMapperDTO = new CountryServiceMapperDTO();
		countryServiceMapperDTO.setCountryCode("US");
		countryServiceMapperDTO.setCountryShortName("USA");
		countryServiceMapperDTO.setGeopoliticalId(id);
		countryServiceMapperDTO.setOrganizationStandardCode("ISO");
		countryServiceMapperDTO.setEffectiveDate(effectiveDate);
		countryServiceMapperDTO.setExpirationDate(expirationDate);
		
		MetaDTO metaResponse = new MetaDTO();
		MessageDTO message = new MessageDTO();
		metaResponse.setVersion("1.0.0");
		metaResponse.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaResponse.setStatusCode("200");
		message.setStatus(ResponseStatus.SUCCESS);
		message.setInternalMessage(GenericConstants.CONSTANT_INTERNAL_MSG);
		metaResponse.setMessage(message);
		
		// by geo id & locale code
		QueryServiceResponseDTO queryServiceResponse = addressLabelService.search(addressServiceMapperDTO);
		
		queryServiceResponse.setMeta(metaResponse);
	
		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);

		// by geo id
		addressServiceMapperDTO.setLocaleCode(null);
		queryServiceResponse = addressLabelService.search(addressServiceMapperDTO);
		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);

		// by only date
		addressServiceMapperDTO.setGeopoliticalId(null);
		queryServiceResponse = addressLabelService.search(addressServiceMapperDTO);
		Assert.assertEquals(queryServiceResponse.getMeta().getMessage().getStatus(), ResponseStatus.SUCCESS);

	}

	private Optional<List<AddressLabel>> getAddressLabel(String geopoliticalId) {
		List<AddressLabel> list = new ArrayList<>();
		AddressLabel addressLabel = new AddressLabel();

		addressLabel.setApplicableFlag("Y");
		addressLabel.setBrandAddressLineLabelDescription("City");
		addressLabel.setFullAddressLineLabelDescription("Street");
		addressLabel.setExpirationDate(Date.valueOf("9999-12-31"));
		addressLabel.setBrandAddressLineLabelDescription("fdfgdfd");
		addressLabel.setFullAddressLineLabelDescription("fdfgdfd");
		addressLabel.setApplicableFlag("a");
		addressLabel.setExpirationDate(Date.valueOf("9999-12-31"));
		addressLabel.setCreatedByUserId("1");
		addressLabel.setCreatedTmstp(CommonUtilityTest.getCurrentTimeStamp());
		addressLabel.setLastUpdatedByNm("B");
		addressLabel.setLastUpdatedTmstp(CommonUtilityTest.getCurrentTimeStamp());

		addressLabel.setId(new AddressLabelPK(geopoliticalId, Integer.valueOf(1), CommonUtilityTest.getCurrenctDate()));
		list.add(addressLabel);
		return Optional.of(list);
	}

}
