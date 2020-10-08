package com.fedex.geopolitical.repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.dto.CountryOrgsFlattennedDTO;
import com.fedex.geopolitical.model.Country;
import com.fedex.geopolitical.model.CountryPK;

@Repository
public interface CountryRepository extends JpaRepository<Country, CountryPK> {

	Optional<List<Country>> findByCountryCd(String countryCd);

	Optional<List<Country>> findByDependentCountryCd(BigInteger dependentCountryCd);

	Optional<Country> findFirstByCountryCd(String countryCd);

	@Query("select distinct c from Country c where c.id.geopoliticalId=?1")
	Optional<List<Country>> findByGeopoliticalId(String geopoliticalId);
 
	@Query("select distinct c from Country c where c.id.geopoliticalId=?1 and "
			+ "((c.id.effectiveDate<=?2 AND c.expirationDate>=?2) or (c.id.effectiveDate<=?3 AND c.expirationDate>=?3) "
			+ "or (?2<=c.id.effectiveDate AND ?3>=c.expirationDate) or (?2>=c.id.effectiveDate AND ?3<=c.expirationDate))")
	Optional<List<Country>> findByGeopoliticalIdAndDate(String geopoliticalId, Date effectiveDate, Date expirationDate);

	@Query("select distinct c from Country c where c.id.geopoliticalId in (:geopoliticalIds) and "
			+ "((c.id.effectiveDate<=:effectiveDate AND c.expirationDate>=:effectiveDate) or (c.id.effectiveDate<=:expirationDate AND c.expirationDate>=:expirationDate) "
			+ "or (:effectiveDate<=c.id.effectiveDate AND :expirationDate>=c.expirationDate) or (:effectiveDate>=c.id.effectiveDate AND :expirationDate<=c.expirationDate))")
	List<Country> findByGeopoliticalIdsAndDate(List<String> geopoliticalIds, Date effectiveDate, Date expirationDate);

	@Query("select u from Country u where u.countryCd=?1 order by u.id.effectiveDate DESC")
	Optional<List<Country>> findFirstByCountryCdOrderByEffectiveDateDesc(String countryCd, Pageable pageable);

	@Query("select distinct c from Country c where "
			+ "((c.id.effectiveDate<=?1 AND c.expirationDate>=?1) or (c.id.effectiveDate<=?2 AND c.expirationDate>=?2) "
			+ "or (?1<=c.id.effectiveDate AND ?2>=c.expirationDate) or (?1>=c.id.effectiveDate AND ?2<=c.expirationDate))")
	Optional<List<Country>> findByDate(Date effectiveDate, Date expirationDate);
	
	@Query(value="select c.GEOPL_ID as geopoliticalId,c.CNTRY_NUM_CD as countryNumericCode,c.CNTRY_CD as countryCode,c.THREE_CHAR_CNTRY_CD as threeCharacterCountryCode,c.INDPT_FLG as independentFlag,"
	        + " c.PSTL_FORMT_DESC as postalFormatDescription,c.PSTL_FLG as postalFlag,c.PSTL_LTH_NBR as postalLength,c.FIRST_WORK_WK_DAY_NM as firstWorkWeekDayName,c.LAST_WORK_WK_DAY_NM as lastWorkWeekDayName,"
				+ " c.WKEND_FIRST_DAY_NM as weekendFirstDayName,c.INET_DOMN_NM as internetDomainName,c.DEPN_RLTSP_ID as dependentRelationshipId,c.DEPN_CNTRY_CD as dependentCountryCode,c.EFFECTIVE_DT as effectiveDate,"
				+ "c.EXPIRATION_DT as expirationDate,c.INTL_DIAL_CD as internationalDialingCode,c.LAND_PH_MAX_LTH_NBR as landPhoneMaximumLength,c.LAND_PH_MIN_LTH_NBR as landPhoneMinimumLength,"
	        + " c.MOBL_PH_MAX_LTH_NBR as mobilePhoneMaximumLength,c.MOBL_PH_MIN_LTH_NBR as mobilePhoneMinimumLength,c.PH_NBR_PATRN_DESC as phoneNumberFormatPattern,"
		   + " cos.ORG_STD_CD as organizationStandardCode,gos.ORG_STD_NM as organizationStandardName,cos.CNTRY_FULL_NM as countryFullName,cos.CNTRY_SHT_NM as countryShortName,"
		   + " cos.EFFECTIVE_DT as cosEffectiveDate,"
		   + " cos.EXPIRATION_DT as cosExpirationDate,"
            +" cu.CURR_NUM_CD as currencyNumericCode,cu.CURR_CD as currencyCode,cu.MINOR_UNIT_CD as minorUnitCode,"
            +" cu.MONEY_FORMT_DESC as moneyFormatDescription,cu.EFFECTIVE_DT as cuEffectiveDate,cu.EXPIRATION_DT as  cuExpirationDate"
       +" from country c" 
       +" left outer join cntry_org_std cos on c.geopl_id=cos.geopl_id"
       +" left outer join GEOPL_ORG_STD gos on cos.ORG_STD_CD = gos.ORG_STD_CD"
       +" left outer join currency cu on cos.geopl_id=cu.geopl_id"    
       +" where ((c.effective_Dt<=?1 AND c.expiration_Dt>=?1) OR (c.effective_Dt<=?2 AND c.expiration_Dt>=?2)" 
       +" OR (?1<=c.effective_Dt AND ?2>=c.expiration_Dt) OR (?1>=c.effective_Dt AND ?2<=c.expiration_Dt)) order by c.geopl_id asc", nativeQuery=true ) 
	//@Cacheable(value="getAllCountries",cacheManager="countriesCacheManager",sync=true)
	List<CountryOrgsFlattennedDTO> findByDateNew(Date effectiveDate, Date expirationDate);



	@Query("select distinct c from Country c where c.id.geopoliticalId=?1")
	List<Country> findByGeoplId(String geopoliticalId);



}
