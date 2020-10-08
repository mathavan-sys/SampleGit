package com.fedex.geopolitical.repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.Currency;
import com.fedex.geopolitical.model.CurrencyPK;

@Repository 
public interface CurrencyRepository extends JpaRepository<Currency,CurrencyPK> {
	
	@Query("select u from Currency u where u.id.geopoliticalId=?1 and u.id.currencyNumberCd=?2 order by u.id.effectiveDate DESC")
	Optional<List<Currency>> findFirstByCurrencyNumberCdAndGeopoliticalIdOrderByEffectiveDateDesc(String geopoliticalId, BigInteger currencyNumberCd, Pageable pageable);
	
	@Query("select distinct u from Currency u where u.id.geopoliticalId=?1 and "
			+ "((u.id.effectiveDate<=?2 AND u.expirationDate>=?2) or (u.id.effectiveDate<=?3 AND u.expirationDate>=?3) "
			+ "or (?2<=u.id.effectiveDate AND ?3>=u.expirationDate) or (?2>=u.id.effectiveDate AND ?3<=u.expirationDate))")
	Optional<List<Currency>> findByGeopoliticalId(String geopoliticalId, Date effectiveDate, Date expirationDate);
	
	@Query("select distinct u from Currency u where "
			+ "((u.id.effectiveDate<=?1 AND u.expirationDate>=?1) or (u.id.effectiveDate<=?2 AND u.expirationDate>=?2) "
			+ "or (?1<=u.id.effectiveDate AND ?2>=u.expirationDate) or (?1>=u.id.effectiveDate AND ?2<=u.expirationDate)) order by u.id.geopoliticalId asc")
	Optional<List<Currency>> findByAllCurrencies(Date effectiveDate, Date expirationDate);
	
}