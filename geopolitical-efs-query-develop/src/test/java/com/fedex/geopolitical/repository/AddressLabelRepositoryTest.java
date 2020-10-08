package com.fedex.geopolitical.repository;

import java.sql.Date;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fedex.geopolitical.model.AddressLabel;
import com.fedex.geopolitical.model.AddressLabelPK;
import com.fedex.geopolitical.utility.CommonUtilityTest;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AddressLabelRepositoryTest {

	@Autowired
	AddressLabelRepository addressLabelRepository;

	
	@Test
	public void test()
	{
		AddressLabel addressLabel = new AddressLabel();
		addressLabel.setId(new AddressLabelPK("234l",Integer.valueOf(1), CommonUtilityTest.getCurrenctDate()));		
		addressLabel.setApplicableFlag("Y");
		addressLabel.setBrandAddressLineLabelDescription("City");
		addressLabel.setFullAddressLineLabelDescription("Street");
		addressLabel.setExpirationDate(Date.valueOf("2020-06-30"));		
	
		addressLabelRepository.save(addressLabel);
		
		Optional<AddressLabel> result = addressLabelRepository.findById(new AddressLabelPK("234l",Integer.valueOf(1), CommonUtilityTest.getCurrenctDate()));
		Assert.assertEquals(true,result.isPresent());
		Assert.assertEquals(addressLabel.getId().getGeopoliticalId(), result.get().getId().getGeopoliticalId());
		 
	}

	

}
