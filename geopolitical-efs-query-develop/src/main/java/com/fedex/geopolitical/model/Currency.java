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
 * The persistent class for the CURRENCY database table.
 * 
 */
@Entity
@Data
@Table(name="CURRENCY")
public class Currency implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CurrencyPK id;

	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;

	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;

	@Column(name="CURR_CD")
	private String currencyCd;

	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByNm;

	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;

	@Column(name="MINOR_UNIT_CD")
	private BigInteger minorUnitCd;

	@Column(name="MONEY_FORMT_DESC")
	private String moneyFormatDescription;
	
	@Column(name="EXPIRATION_DT")
	private Date expirationDate;


}