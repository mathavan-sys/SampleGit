package com.fedex.geopolitical.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.StProvStd;
import com.fedex.geopolitical.model.StProvStdPK;

@Repository
public interface StProvStdRepository extends JpaRepository<StProvStd, StProvStdPK>{
	
	List<StProvStd> findByStProvCd(String stProvCd);

	Optional<StProvStd> findByStProvNm(String stProvNm);
	
	@Query("select s from StProvStd s where s.id.geopoliticalId=?1 and "
			+ "((s.id.effectiveDate<=?2 AND s.expirationDate>=?2) or (s.id.effectiveDate<=?3 AND s.expirationDate>=?3) "
			+ "or (?2<=s.id.effectiveDate AND ?3>=s.expirationDate) or (?2>=s.id.effectiveDate AND ?3<=s.expirationDate))")
	List<StProvStd> findByGeopoliticalId(String geopoliticalId, Date effectiveDate, Date expirationDate);
	
	@Query("select s from StProvStd s where s.stProvCd=?1 and s.id.orgStdCd=?2 and "
			+ "((s.id.effectiveDate<=?3 AND s.expirationDate>=?3) or (s.id.effectiveDate<=?4 AND s.expirationDate>=?4) "
			+ "or (?3<=s.id.effectiveDate AND ?4>=s.expirationDate) or (?3>=s.id.effectiveDate AND ?4<=s.expirationDate))")
	List<StProvStd> findByStprovCdAndorgStdCd(String stProvCd, String orgStdCd, Date effectiveDate, Date expirationDate);
	
	@Query("select s from StProvStd s where s.stProvCd=?1 and s.id.orgStdCd=?2 and "
			+ "((s.id.effectiveDate<=?3 AND s.expirationDate>=?3) or (s.id.effectiveDate<=?4 AND s.expirationDate>=?4) "
			+ "or (?3<=s.id.effectiveDate AND ?4>=s.expirationDate) or (?3>=s.id.effectiveDate AND ?4<=s.expirationDate))")
	List<StProvStd> findByStprovCdAndorgStdCdAndEffectAndTargetDate(String stProvCd, String orgStdCd, Date effectiveDate, Date targetDate);
	
	@Query("select s from StProvStd s,GeoplRltsp r,Country c where s.id.geopoliticalId=r.id.reltdGeoplComptId and r.id.geoplComptId=c.id.geopoliticalId and c.countryCd=?1 and "
			+ "((s.id.effectiveDate<=?2 AND s.expirationDate>=?2) or (s.id.effectiveDate<=?3 AND s.expirationDate>=?3) "
			+ "or (?2<=s.id.effectiveDate AND ?3>=s.expirationDate) or (?2>=s.id.effectiveDate AND ?3<=s.expirationDate))")
	List<StProvStd> findByCountryCd(String countryCd, Date effectiveDate, Date expirationDate);
	
	@Query("select s from StProvStd s,GeoplRltsp r,Country c where s.id.geopoliticalId=r.id.reltdGeoplComptId and r.id.geoplComptId=c.id.geopoliticalId and s.stProvCd=?1 and c.countryCd=?2 and "
			+ "((s.id.effectiveDate<=?3 AND s.expirationDate>=?3) or (s.id.effectiveDate<=?4 AND s.expirationDate>=?4) "
			+ "or (?3<=s.id.effectiveDate AND ?4>=s.expirationDate) or (?3>=s.id.effectiveDate AND ?4<=s.expirationDate))")
	List<StProvStd> findBystProvCdAndCountryCd(String stProvCd,String countryCd, Date effectiveDate, Date expirationDate);
	
	@Query("select distinct s from StProvStd s,GeoplRltsp r,Country c where s.id.geopoliticalId=r.id.reltdGeoplComptId and r.id.geoplComptId=c.id.geopoliticalId and c.countryCd=?1 and "
			+ "((s.id.effectiveDate<=?2 AND s.expirationDate>=?2) or (s.id.effectiveDate<=?3 AND s.expirationDate>=?3) "
			+ "or (?2<=s.id.effectiveDate AND ?3>=s.expirationDate) or (?2>=s.id.effectiveDate AND ?3<=s.expirationDate)) and ((r.id.effectiveDate<=?2 AND r.expirationDate>=?2) or (r.id.effectiveDate<=?3 AND r.expirationDate>=?3) "
			+ "or (?2<=r.id.effectiveDate AND ?3>=r.expirationDate) or (?2>=r.id.effectiveDate AND ?3<=r.expirationDate)) and ((c.id.effectiveDate<=?2 AND c.expirationDate>=?2) or (c.id.effectiveDate<=?3 AND c.expirationDate>=?3) "
			+ "or (?2<=c.id.effectiveDate AND ?3>=c.expirationDate) or (?2>=c.id.effectiveDate AND ?3<=c.expirationDate))")
	List<StProvStd> findByCountryCdAndTrgtAndEndDate(String countryCd, Date effectiveDate, Date targetDate);
	
	@Query("select s from StProvStd s where "
			+ "((s.id.effectiveDate<=?1 AND s.expirationDate>=?1) or (s.id.effectiveDate<=?2 AND s.expirationDate>=?2) "
			+ "or (?1<=s.id.effectiveDate AND ?2>=s.expirationDate) or (?1>=s.id.effectiveDate AND ?2<=s.expirationDate))")
	List<StProvStd> findAllByEffAndExpDate(Date effectiveDate, Date expirationDate);
}
