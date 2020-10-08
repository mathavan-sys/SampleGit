package com.fedex.geopolitical.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.Country;
import com.fedex.geopolitical.model.Locale;
import com.fedex.geopolitical.model.LocalePK;
import com.fedex.geopolitical.model.TrnslMthOfYr;

@Repository
public interface LocaleRepository extends JpaRepository<Locale, LocalePK>{

	@Query("select u from Locale u where u.geopoliticalId=?1 and u.id.loclcode=?2 order by u.id.effectiveDate DESC")
	Optional<List<Locale>> findFirstByLocaleCdAndGeopoliticalIdOrderByEffectiveDateDesc(String geopoliticalId, String localeCd, Pageable pageable);
	
	@Query("select distinct u from Locale u where u.geopoliticalId=?1 and "
			+ "((u.id.effectiveDate<=?2 AND u.expirationDate>=?2) or (u.id.effectiveDate<=?3 AND u.expirationDate>=?3) "
			+ "or (?2<=u.id.effectiveDate AND ?3>=u.expirationDate) or (?2>=u.id.effectiveDate AND ?3<=u.expirationDate))")
	Optional<List<Locale>> findByGeopoliticalId(String geopoliticalId, Date effectiveDate, Date expirationDate);
	
	//@Query("select m from Locale m where m.langCd=?1")
	Optional<List<Locale>> findByLangCd(String langCd);
	
	@Query("select m from Locale m where m.id.loclcode=?1")
	Optional<List<Locale>> findByLoclcode(String loclcode);
	
	Optional<List<Locale>> findByGeopoliticalId(String geopoliticalId);
	
	@Query("select m from Locale m where m.id.loclcode=?1")
	List<Locale> findByLocalecode(List<String> loclcode);
	@Query("select m from Locale m where m.geopoliticalId=?1")
	List<Locale> findByGeoplId(String geopoliticalId);
}
