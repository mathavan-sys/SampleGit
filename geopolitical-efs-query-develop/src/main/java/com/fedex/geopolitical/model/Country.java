package com.fedex.geopolitical.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;


/**
 * The persistent class for the COUNTRY database table.
 * 
 */
@Entity
@Data
@Table(name="COUNTRY")
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CountryPK id;

	@Column(name="CNTRY_CD")
	private String countryCd;

	@Column(name="CNTRY_NUM_CD")
	private BigInteger countryNumberCd;

	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;

	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;

	@Column(name="DEPN_CNTRY_CD")
	private BigInteger dependentCountryCd;

	@Column(name="FIRST_WORK_WK_DAY_NM")
	private String firstWorkWeekDayName;

	@Column(name="INDPT_FLG")
	private String independentFlag;

	@Column(name="INET_DOMN_NM")
	private String internetDomainName;

	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByNm;

	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;

	@Column(name="LAST_WORK_WK_DAY_NM")
	private String lastWorkWeekDayName;

	@Column(name="PSTL_FLG")
	private String postalFlag;

	@Column(name="PSTL_FORMT_DESC")
	private String postalFormatDescription;

	@Column(name="PSTL_LTH_NBR")
	private BigInteger postalLengthNumber;

	@Column(name="THREE_CHAR_CNTRY_CD")
	private String threeCharCountryCd;

	@Column(name="WKEND_FIRST_DAY_NM")
	private String weekendFirstDayName;

	@Column(name="EXPIRATION_DT")
	private Date expirationDate;
	
	@Column(name="DEPN_RLTSP_ID")
	private String dependentRelationshipId;
	
	@Column(name="PH_NBR_PATRN_DESC")
	private String phoneNumberPatternDescription; 
	
	@Column(name="INTL_DIAL_CD")
	private String internationalDialingCode;
	
	@Column(name="LAND_PH_MIN_LTH_NBR")
	private BigInteger landPhoneMinimumLengthNumber;
	
	@Column(name="LAND_PH_MAX_LTH_NBR")
	private BigInteger landPhoneMaximumLengthNumber;
	
	@Column(name="MOBL_PH_MIN_LTH_NBR")
	private BigInteger mobilePhoneMinimumLengthNumber;
	
	@Column(name="MOBL_PH_MAX_LTH_NBR")
	private BigInteger mobilePhoneMaximumLengthNumber;
	
	
	/*
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "country") private
	 * List<Geopolitical> geopoliticals;
	 */
	
}