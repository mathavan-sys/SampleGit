package com.fedex.geopolitical.repository;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedex.geopolitical.model.GeoplRltsp;
import com.fedex.geopolitical.model.GeoplRltspPK;
import com.fedex.geopolitical.model.GeopoliticalRelationshipType;
import com.fedex.geopolitical.utility.CommonUtilityTest;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode=ClassMode.BEFORE_CLASS)
public class GeopoliticalRelationshipRepositoryTest {
	
	@Autowired
	GeoplRltspRepository geoplRltsprepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	GeopoliticalRelationshipTypeRepository geopoliticalRelationshipTypeRepository;
	
	@Rollback(true)
	@Test
	public void test() throws JsonParseException, JsonMappingException, IOException, ParseException
	{
		
		GeopoliticalRelationshipType geopoliticalRelationshipType = new GeopoliticalRelationshipType();
		geopoliticalRelationshipType.setGeopoliticalRelationshipTypeCd("10");
		geopoliticalRelationshipType.setAreaRelationshipTypeDescription("Christmas");
		geopoliticalRelationshipType.setCreatedByUserId("3766271");
		geopoliticalRelationshipType.setLastUpdatedByNm("3766271");
		geopoliticalRelationshipTypeRepository.save(geopoliticalRelationshipType);
		
		GeoplRltsp geoplRltsp = new GeoplRltsp();
		GeoplRltspPK id= new GeoplRltspPK();
		id.setGeoplComptId("2");
		id.setReltdGeoplComptId("3");
		id.setGeoplRltspTypeCd("10");
		id.setEffectiveDate(Date.valueOf(CommonUtilityTest.getCurrenctDate()));
		geoplRltsp.setId(id);
		geoplRltsp.setCreatedByUserId("37663271");
		geoplRltsp.setLastUpdatedByNm("37663271");
		geoplRltsp.setGeoplRltspType(geopoliticalRelationshipType);
		geoplRltsprepository.save(geoplRltsp);
		
	}
	
}
