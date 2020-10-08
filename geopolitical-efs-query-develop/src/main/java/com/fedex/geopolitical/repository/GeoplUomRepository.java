package com.fedex.geopolitical.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.GeoplUom;
import com.fedex.geopolitical.model.GeoplUomPK;

@Repository
public interface GeoplUomRepository extends JpaRepository<GeoplUom, GeoplUomPK>{

	@Query("select u from GeoplUom u where u.id.geopoliticalId=?1 and u.id.uomTypeCd=?2 order by u.id.effectiveDate DESC")
	Optional<List<GeoplUom>> findFirstByUomTypeCdAndGeopoliticalIdOrderByEffectiveDateDesc(String geopoliticalId, String uomTypeCd, Pageable pageable);
	
	@Query("select distinct u from GeoplUom u where u.id.geopoliticalId=?1 and "
			+ "((u.id.effectiveDate<=?2 AND u.expirationDate>=?2) or (u.id.effectiveDate<=?3 AND u.expirationDate>=?3) "
			+ "or (?2<=u.id.effectiveDate AND ?3>=u.expirationDate) or (?2>=u.id.effectiveDate AND ?3<=u.expirationDate))")
	Optional<List<GeoplUom>> findByGeopoliticalId(String geopoliticalId, Date effectiveDate, Date expirationDate);
	
}
