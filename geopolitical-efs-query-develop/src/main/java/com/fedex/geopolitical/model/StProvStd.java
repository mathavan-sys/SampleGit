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
 * The persistent class for the ST_PROV_STD database table.
 * 
 */
@Entity
@Data
@Table(name="ST_PROV_STD")
public class StProvStd implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private StProvStdPK id;

	@Column(name="ST_PROV_CD")
	private String stProvCd;

	@Column(name="ST_PROV_NM")
	private String stProvNm;

	//bi-directional many-to-one association to GeoplOrgStd
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ORG_STD_CD", foreignKey=@ForeignKey(name="none"), insertable = false, updatable = false)
	private GeoplOrgStd geoplOrgStd;
	
	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;
	
	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;
	
	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;
	
	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByUserId;
	
	@Column(name="EXPIRATION_DT")
	private Date expirationDate;


}