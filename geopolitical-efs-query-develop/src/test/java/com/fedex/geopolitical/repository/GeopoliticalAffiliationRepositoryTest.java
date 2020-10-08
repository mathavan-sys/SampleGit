package com.fedex.geopolitical.repository;

import java.sql.Date;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fedex.geopolitical.model.GeoplAffil;
import com.fedex.geopolitical.model.GeoplAffilPK;
import com.fedex.geopolitical.model.GeoplAffilType;
import com.fedex.geopolitical.utility.CommonUtilityTest;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext
public class GeopoliticalAffiliationRepositoryTest {

	@Autowired
	GeoplAffilRepository geoplAffilRepository;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	GeoplAffilTypeRepository geoplAffilTypeRepository;

	@Before
	public void setup() {
		GeoplAffilType geoplAffilType = getGeoplAffilType("EN");
		geoplAffilTypeRepository.save(geoplAffilType);
		GeoplAffil geoplAffil = getGeopoliticalAffiliation(11l, "1");
		geoplAffilRepository.save(geoplAffil);
	}

	@Test
	public void test() {
		GeoplAffil geoplAffil = getGeopoliticalAffiliation(11l, "1");

		Optional<GeoplAffil> result = geoplAffilRepository
				.findById(new GeoplAffilPK(11l, "1", Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		Assert.assertEquals(true, result.isPresent());
		Assert.assertEquals(geoplAffil.getId().getGeopoliticalId(), result.get().getId().getGeopoliticalId());
		Assert.assertEquals(geoplAffil.getId().getAffilTypeId(), result.get().getId().getAffilTypeId());
	}

	private GeoplAffil getGeopoliticalAffiliation(Long affilTypeId, String geopoliticalId) {
		GeoplAffil geoplAffil = new GeoplAffil();
		GeoplAffilPK pk = new GeoplAffilPK(affilTypeId, geopoliticalId,
				Date.valueOf(CommonUtilityTest.getCurrenctDate()));
		geoplAffil.setId(pk);
		return geoplAffil;
	}

	private GeoplAffilType getGeoplAffilType(String code) {
		GeoplAffilType affilType = new GeoplAffilType();
		affilType.setAffilTypeId(11l);
		affilType.setAffilTypeCode(code);
		return affilType;
	}

}
