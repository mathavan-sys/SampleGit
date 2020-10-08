package com.fedex.geopolitical.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * The persistent class for the TRNSL_GEOPL database table.
 * 
 */
@Entity
@Data
@Table(name = "TRNSL_GEOPL")
public class TrnslGeopl implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TrnslGeoplPK id;

	@Column(name = "CREATED_BY_USER_ID")
	private String createdByUserId;

	@Column(name = "CREATED_TMSTP")
	private Timestamp createdTmstp;

	@Column(name = "LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByNm;

	@Column(name = "LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;

	@Column(name = "TRNSL_NM")
	private String translationName;

	@Column(name = "VERS_DT")
	private Date versionDate;

	@Column(name = "VERS_NBR")
	private String versionNumber;

	@Column(name = "EXPIRATION_DT")
	private Date expirationDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCRIPT_CD", foreignKey = @ForeignKey(name = "none"), insertable = false, updatable = false)
	private RefScript refScript;

}