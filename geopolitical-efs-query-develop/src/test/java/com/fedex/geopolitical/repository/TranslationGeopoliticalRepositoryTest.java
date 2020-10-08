/*package com.fedex.geopolitical.repository;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fedex.geopolitical.model.Geopolitical;
import com.fedex.geopolitical.model.TrnslGeopl;
import com.fedex.geopolitical.model.TrnslGeoplPK;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TranslationGeopoliticalRepositoryTest {

	@Autowired
	TrnslGeoplRepository trnslGeoplRepository;

	@Autowired
	CountryRepository countryRepository;

	@Before
	public void setup(){
		Country country = getCountry("IN", new BigDecimal(2l), "abc");
		Geopolitical geopolitical = getGeopolitical(1l, 11l);
		TrnslGeopl trnslGeopl = getTranslationGeopolitical("EN", 1l);
		Set<TrnslGeopl> trnslationGeopoliticals = new HashSet<>();
		trnslationGeopoliticals.add(trnslGeopl);
		geopolitical.setTrnslationGeopoliticals(trnslationGeopoliticals);
		country.setGeopolitical(geopolitical);
		countryRepository.save(country);
	}

	@Test
	public void test()
	{
		TrnslGeopl trnslGeopl = getTranslationGeopolitical("EN", 1l);

		Optional<TrnslGeopl> result = trnslGeoplRepository.findById(new TrnslGeoplPK(1l, "EN"));
		Assert.assertEquals(true,result.isPresent());
		Assert.assertEquals(trnslGeopl.getId().getGeopoliticalId(), result.get().getId().getGeopoliticalId());
		Assert.assertEquals(trnslGeopl.getId().getLanguageCd(), result.get().getId().getLanguageCd());
		
		trnslGeoplRepository.delete(trnslGeopl);
		result = trnslGeoplRepository.findById(new TrnslGeoplPK(1l, "EN"));
		Assert.assertEquals(false,result.isPresent());
	}

	private Geopolitical getGeopolitical(String geopoliticalId, long geopoliticalTypeId)
	{
		Geopolitical geopolitical = new Geopolitical();
		geopolitical.getId().setGeopoliticalId(geopoliticalId);
		geopolitical.setGeopoliticalTypeId(geopoliticalTypeId);
		return geopolitical;
	}
	
	
	private TrnslGeopl getTranslationGeopolitical(String languageCd, String scriptCd, String geopoliticalId)
	{
		TrnslGeopl trnslGeopl = new TrnslGeopl();
		TrnslGeoplPK pk = new TrnslGeoplPK(geopoliticalId, languageCd, Calendar.getInstance().getTime(), scriptCd);
		trnslGeopl.setId(pk);
		return trnslGeopl;
	}

}
*/