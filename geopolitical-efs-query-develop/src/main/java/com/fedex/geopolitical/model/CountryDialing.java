package com.fedex.geopolitical.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;


/**
 * The persistent class for the CNTRY_DIAL database table.
 * 
 */
@Entity
@Data
@Table(name="CNTRY_DIAL")
public class CountryDialing implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CountryDialingPK id;

	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;

	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;

	@Column(name="INTL_DIAL_CD")
	private String intialDialingCd;

	@Column(name="LAND_PH_MAX_LTH_NBR")
	private BigInteger landPhMaxLthNbr;

	@Column(name="LAND_PH_MIN_LTH_NBR")
	private BigInteger landPhMinLthNbr;

	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByNm;

	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;

	@Column(name="MOBL_PH_MAX_LTH_NBR")
	private BigInteger moblPhMaxLthNbr;

	@Column(name="MOBL_PH_MIN_LTH_NBR")
	private BigInteger moblPhMinLthNbr;

	@Column(name="EXPIRATION_DT")
	private Date expirationDate;
	
}