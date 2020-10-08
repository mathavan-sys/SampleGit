package com.fedex.geopolitical.repository;

import java.io.IOException;

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


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode=ClassMode.BEFORE_CLASS)
public class CntryOrgStdRepositoryTest {
	
	@Autowired
	GeoplOrgStdRepository geolOrgStdRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	CntryOrgStdRepository cntryStdRepository;

	@Rollback(true)
	@Test
	public void test() throws JsonParseException, JsonMappingException, IOException
	{
		
	}
	
}
