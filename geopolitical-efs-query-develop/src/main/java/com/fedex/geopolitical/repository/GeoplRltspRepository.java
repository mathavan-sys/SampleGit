package com.fedex.geopolitical.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.GeoplRltsp;
import com.fedex.geopolitical.model.GeoplRltspPK;

@Repository
public interface GeoplRltspRepository extends JpaRepository<GeoplRltsp, GeoplRltspPK> {

	@Query("select Max(g.id.effectiveDate) from GeoplRltsp g where g.id.geoplComptId=?1 and g.id.reltdGeoplComptId=?2 and g.id.geoplRltspTypeCd=?3")
	Date findByGeopoliticalIdAndOrgStdCdAndTypeCode(String geoplComptId, String reltdGeoplComptId,
			String geoplRltspTypeCd);

	@Query("select g from GeoplRltsp g where g.id.geoplComptId=?1 and g.id.reltdGeoplComptId=?2 and g.id.geoplRltspTypeCd=?3 and "
			+ "((g.id.effectiveDate<=?4 AND g.expirationDate>=?4) or (g.id.effectiveDate<=?5 AND g.expirationDate>=?5) "
			+ "or (?4<=g.id.effectiveDate AND ?5>=g.expirationDate) or (?4>=g.id.effectiveDate AND ?5<=g.expirationDate))")
	List<GeoplRltsp> findByIDAndEndDate(String geoplComptId, String reltdGeoplComptId, String geoplRltspTypeCd,
			Date effectiveDate, Date expirationDate);

	@Query("select g from GeoplRltsp g where g.id.geoplRltspTypeCd=?1 and "
			+ "((g.id.effectiveDate<=?2 AND g.expirationDate>=?2) or (g.id.effectiveDate<=?3 AND g.expirationDate>=?3) "
			+ "or (?2<=g.id.effectiveDate AND ?3>=g.expirationDate) or (?2>=g.id.effectiveDate AND ?3<=g.expirationDate))")
	List<GeoplRltsp> findByRltspTypeCodeAndTargetDateAndEndDate(String geoplRltspTypeCd, Date effectiveDate,
			Date expirationDate);

	@Query("select g from GeoplRltsp g where g.id.geoplComptId=?1 and g.id.reltdGeoplComptId=?2")
	List<GeoplRltsp> findByGeoplComptIdAndReltdGeoplComptId(String geoplComptId, String reltdGeoplComptId);

	@Query("select s from GeoplRltsp s where s.id=?1")
	List<GeoplRltsp> findByPKId(GeoplRltspPK id);

	@Query("select g from GeoplRltsp g where g.id.reltdGeoplComptId=?1 and g.id.geoplRltspTypeCd=?2 and "
			+ "((g.id.effectiveDate<=?3 AND g.expirationDate>=?3) or (g.id.effectiveDate<=?4 AND g.expirationDate>=?4) "
			+ "or (?3<=g.id.effectiveDate AND ?4>=g.expirationDate) or (?3>=g.id.effectiveDate AND ?4<=g.expirationDate))")
	List<GeoplRltsp> findByGeoplRelatedComptIdAndCode(String reltdGeoplComptId, String geoplRltspTypeCd,
			Date effectiveDate, Date expirationDate);

	@Query("select g from GeoplRltsp g where g.id.geoplComptId=?1 and g.id.geoplRltspTypeCd=?2 and "
			+ "((g.id.effectiveDate<=?3 AND g.expirationDate>=?3) or (g.id.effectiveDate<=?4 AND g.expirationDate>=?4) "
			+ "or (?3<=g.id.effectiveDate AND ?4>=g.expirationDate) or (?3>=g.id.effectiveDate AND ?4<=g.expirationDate))")
	List<GeoplRltsp> findByGeoplComptIdAndCode(String geoplComptId, String geoplRltspTypeCd, Date effectiveDate,
			Date expirationDate);

	@Query("select g from GeoplRltsp g where g.id.geoplRltspTypeCd=?1")
	List<GeoplRltsp> findByRltspTypeCode(String geoplRltspTypeCd);

	@Query("select g from GeoplRltsp g where "
			+ "((g.id.effectiveDate<=?1 AND g.expirationDate>=?1) or (g.id.effectiveDate<=?2 AND g.expirationDate>=?2) "
			+ "or (?1<=g.id.effectiveDate AND ?2>=g.expirationDate) or (?1>=g.id.effectiveDate AND ?2<=g.expirationDate))")
	List<GeoplRltsp> findAllByEffAndExpDate(Date effectiveDate, Date expirationDate);

}
