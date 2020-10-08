package com.fedex.geopolitical.repository;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.fedex.geopolitical.model.GeoplOrgStd;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GeopoliticalOrgStdRepositoryTest {

	@Autowired
	GeoplOrgStdRepository geoplOrgStdRepository;

	@Test
	public void testGeopoliticalOrgStdRepository() {
		GeoplOrgStd geopoliticalOrgStd = getGeopoliticalOrgStd("Christmas");
		geoplOrgStdRepository.save(geopoliticalOrgStd);

		Optional<GeoplOrgStd> result = geoplOrgStdRepository.findByOrgStdCd("ISO");
		Assert.assertEquals(true, result.isPresent());
		Assert.assertEquals(geopoliticalOrgStd.getOrgStdCd(), result.get().getOrgStdCd());
		Assert.assertEquals(geopoliticalOrgStd.getOrgStdNm(), result.get().getOrgStdNm());
		Assert.assertEquals(geopoliticalOrgStd.getCreatedByUserId(), result.get().getCreatedByUserId());
		Assert.assertEquals(geopoliticalOrgStd.getLastUpdatedByUserId(), result.get().getLastUpdatedByUserId());

		geoplOrgStdRepository.delete(geopoliticalOrgStd);
		result = geoplOrgStdRepository.findByOrgStdCd("ISO");
		Assert.assertEquals(false, result.isPresent());

	}

	private GeoplOrgStd getGeopoliticalOrgStd(String geopoliticalTypeName) {
		GeoplOrgStd geopoliticalOrgStd = new GeoplOrgStd();
		geopoliticalOrgStd.setOrgStdCd("ISO");
		geopoliticalOrgStd.setOrgStdNm("International Standard Organization");
		geopoliticalOrgStd.setCreatedByUserId("3766271");
		geopoliticalOrgStd.setLastUpdatedByUserId("3766271");
		return geopoliticalOrgStd;
	}

}
