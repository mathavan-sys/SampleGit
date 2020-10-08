package com.fedex.geopolitical.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.AddressLabel;
import com.fedex.geopolitical.model.AddressLabelPK;

@Repository
public interface AddressLabelRepository extends JpaRepository<AddressLabel, AddressLabelPK> {

	@Query("select a from AddressLabel a where  a.id.geopoliticalId=?1 and "
			+ "((a.effectiveDate<=?2 AND a.expirationDate>=?2) or (a.effectiveDate<=?3 AND a.expirationDate>=?3) "
			+ "or (?2<=a.effectiveDate AND ?3>=a.expirationDate) or (?2>=a.effectiveDate AND ?3<=a.expirationDate)) order by a.id.addressLineNumber ASC")
	Optional<List<AddressLabel>> findByGeopoliticalId(String geopoliticalId, Date effectiveDate, Date expirationDate);

	@Query("select a from AddressLabel a where a.id.geopoliticalId=?1 and a.id.loclcode=?2 and "
			+ "((a.effectiveDate<=?3 AND a.expirationDate>=?3) or (a.effectiveDate<=?4 AND a.expirationDate>=?4) "
			+ "or (?3<=a.effectiveDate AND ?4>=a.expirationDate) or (?3>=a.effectiveDate AND ?4<=a.expirationDate)) order by a.id.addressLineNumber ASC")
	Optional<List<AddressLabel>> findByGeopoliticalIdAndLocale(String geopoliticalId, String loclcode,
			Date effectiveDate, Date expirationDate);

	@Query("select a from AddressLabel a  where "
			+ "((a.effectiveDate<=?1 AND a.expirationDate>=?1) or (a.effectiveDate<=?2 AND a.expirationDate>=?2) "
			+ "or (?1<=a.effectiveDate AND ?2>=a.expirationDate) or (?1>=a.effectiveDate AND ?2<=a.expirationDate)) order by a.id.addressLineNumber ASC")
	Optional<List<AddressLabel>> findAllByDate(Date effectiveDate, Date expirationDate);

	@Query("select a from AddressLabel a where a.id.geopoliticalId=?1 and a.id.loclcode=?2 AND a.id.addressLineNumber=?3")
	Optional<List<AddressLabel>> findByGeopoliticalIdAndLocaleAndAddressLineNumber(String geopoliticalId,
			String loclcode, int addressLineNumber);

	@Query("select a from AddressLabel a where a.id.geopoliticalId=?1 and a.id.loclcode=?2 AND a.id.addressLineNumber=?5 and "
			+ "((a.effectiveDate<=?3 AND a.expirationDate>=?3) or (a.effectiveDate<=?4 AND a.expirationDate>=?4) "
			+ "or (?3<=a.effectiveDate AND ?4>=a.expirationDate) or (?3>=a.effectiveDate AND ?4<=a.expirationDate))")
	Optional<List<AddressLabel>> findByGeopoliticalIdAndLocaleAndAddressLineNumber(String geopoliticalId,
			String loclcode, Date effectiveDate, Date expirationDate, int addressLineNumber);
	
	@Query("select a from AddressLabel a where "
			+ "((a.effectiveDate<=?1 AND a.expirationDate>=?1) or (a.effectiveDate<=?2 AND a.expirationDate>=?2) "
			+ "or (?2<=a.effectiveDate AND ?2>=a.expirationDate) or (?1>=a.effectiveDate AND ?2<=a.expirationDate)) order by a.id.geopoliticalId,a.id.loclcode,a.id.addressLineNumber asc")
	Optional<List<AddressLabel>> findByAllAddressLabels(Date effectiveDate, Date expirationDate);

}
