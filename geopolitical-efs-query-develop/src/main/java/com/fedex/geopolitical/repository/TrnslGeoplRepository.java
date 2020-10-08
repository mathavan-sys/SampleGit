package com.fedex.geopolitical.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.TrnslGeopl;
import com.fedex.geopolitical.model.TrnslGeoplPK;

@Repository
public interface TrnslGeoplRepository extends JpaRepository<TrnslGeopl, TrnslGeoplPK> {

	@Query("select distinct u from TrnslGeopl u where u.id.geopoliticalId=?1 and "
			+ "((u.id.effectiveDate<=?2 AND u.expirationDate>=?2) or (u.id.effectiveDate<=?3 AND u.expirationDate>=?3) "
			+ "or (?2<=u.id.effectiveDate AND ?3>=u.expirationDate) or (?2>=u.id.effectiveDate AND ?3<=u.expirationDate))")
	Optional<List<TrnslGeopl>> findByGeopoliticalId(String geopoliticalId, Date effectiveDate, Date expirationDate);

}
