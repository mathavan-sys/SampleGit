package com.fedex.geopolitical.repository;

import java.sql.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fedex.geopolitical.model.GeoplUom;
import com.fedex.geopolitical.model.GeoplUomPK;
import com.fedex.geopolitical.model.RefUomType;
import com.fedex.geopolitical.repository.CountryRepository;
import com.fedex.geopolitical.repository.GeoplUomRepository;
import com.fedex.geopolitical.repository.RefUomTypeRepository;
import com.fedex.geopolitical.utility.CommonUtilityTest;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GeopoliticalUnitOfMeasureRepositoryTest {

	@Autowired
	GeoplUomRepository geoplUomRepository;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	RefUomTypeRepository refUomTypeRepository;

	@Before
	public void setup() {
		RefUomType refUomType = getRefUomType("EN");
		refUomTypeRepository.save(refUomType);

		GeoplUom geoplUom = getGeopoliticalUnitOfMeasure("EN", "1");
		Set<GeoplUom> geopoliticalUoms = new HashSet<>();
		geopoliticalUoms.add(geoplUom);
		geoplUomRepository.save(geoplUom);
	}

	@Test
	public void test() {
		GeoplUom geoplUom = getGeopoliticalUnitOfMeasure("EN", "1");

		Optional<GeoplUom> result = geoplUomRepository
				.findById(new GeoplUomPK("EN", "1", Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		Assert.assertEquals(true, result.isPresent());
		Assert.assertEquals(geoplUom.getId().getGeopoliticalId(), result.get().getId().getGeopoliticalId());
		Assert.assertEquals(geoplUom.getId().getUomTypeCd(), result.get().getId().getUomTypeCd());

		geoplUomRepository.delete(geoplUom);
		result = geoplUomRepository
				.findById(new GeoplUomPK("EN", "1", Date.valueOf(CommonUtilityTest.getCurrenctDate())));
		Assert.assertEquals(false, result.isPresent());
	}

	private GeoplUom getGeopoliticalUnitOfMeasure(String uomTypeCd, String geopoliticalId) {
		GeoplUom geoplUom = new GeoplUom();
		GeoplUomPK pk = new GeoplUomPK(uomTypeCd, geopoliticalId, Date.valueOf(CommonUtilityTest.getCurrenctDate()));
		geoplUom.setId(pk);
		return geoplUom;
	}

	private RefUomType getRefUomType(String refUomTypeNm) {
		RefUomType refUomType = new RefUomType();
		refUomType.setUomTypeCd("EN");
		refUomType.setUomTypeDesc("EN");
		refUomType.setUomTypeNm(refUomTypeNm);
		return refUomType;
	}

}
