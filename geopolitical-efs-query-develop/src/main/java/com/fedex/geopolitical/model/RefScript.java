package com.fedex.geopolitical.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


/**
 * The persistent class for the REF_SCRIPT database table.
 * 
 */
@Entity
@Data
@Table(name="SCRIPT")
public class RefScript implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SCRPT_CD")
	private String scrptCd;

	@Column(name="SCRPT_DESC")
	private String scrptDesc;

	@Column(name="SCRPT_NM")
	private String scrptNm;
	
	@Column(name="EFFECTIVE_DT")
	private Date effectiveDate; 
	
	@Column(name = "EXPIRATION_DT")
	private Date expirationDate;

	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;
	
	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;
	
	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByUserId;
	
	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;
	

	
}