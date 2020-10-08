package com.fedex.geopolitical.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.CntryOrgStd;
import com.fedex.geopolitical.model.CntryOrgStdPK;

@Repository
public interface CntryOrgStdRepository extends JpaRepository<CntryOrgStd, CntryOrgStdPK>, JpaSpecificationExecutor<CntryOrgStd> {

	@Query("select c from CntryOrgStd c where c.id<>?1 and c.cntryShtNm=?2")
	Optional<CntryOrgStd> findByCntryShtNmAndNotId(CntryOrgStdPK id, String cntryShtNm);

	@Query("select c from CntryOrgStd c where c.id<>?1 and c.cntryFullNm=?2")
	Optional<CntryOrgStd> findByCntryFullNmAndNotId(CntryOrgStdPK id, String cntryFullNm);

	@Query("select Max(g.id.effectiveDate) from CntryOrgStd g where g.id.geopoliticalId=?1 and g.id.orgStdCd=?2")
	Date findByGeopoliticalIdAndOrgStdCd(String geopoliticalId, String orgStdCd);

	@Query("select g from CntryOrgStd g where g.id.geopoliticalId=?1 and g.id.orgStdCd=?2")
	Optional<CntryOrgStd> findByGeopoliticalIdAndOrgStdCode(String geopoliticalId, String orgStdCd);

	@Query("select c from CntryOrgStd c where lower(c.cntryShtNm) like lower(?1) and lower(c.id.orgStdCd) like lower(?2)")
	Optional<List<CntryOrgStd>> findByCntryShtNmAndOrgStdCode(String cntryShtNm, String orgStdCd);

	@Query("select distinct u from CntryOrgStd u where u.id.geopoliticalId=?1 and "
			+ "((u.id.effectiveDate<=?2 AND u.expirationDate>=?2) or (u.id.effectiveDate<=?3 AND u.expirationDate>=?3) "
			+ "or (?2<=u.id.effectiveDate AND ?3>=u.expirationDate) or (?2>=u.id.effectiveDate AND ?3<=u.expirationDate))")
	Optional<List<CntryOrgStd>> findByGeopoliticalId(String geopoliticalId, Date effectiveDate, Date expirationDate);
	
	@Query("select distinct u from CntryOrgStd u where "
			+ "((u.id.effectiveDate<=?1 AND u.expirationDate>=?1) or (u.id.effectiveDate<=?2 AND u.expirationDate>=?2) "
			+ "or (?1<=u.id.effectiveDate AND ?2>=u.expirationDate) or (?1>=u.id.effectiveDate AND ?2<=u.expirationDate))")
	Optional<List<CntryOrgStd>> findByAllCntryOrgStd(Date effectiveDate, Date expirationDate);
}
