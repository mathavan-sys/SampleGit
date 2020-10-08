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
 * The persistent class for the GEOPL_RLTSP database table.
 * 
 */
@Entity
@Data
@Table(name="GEOPL_RLTSP")
public class GeoplRltsp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;
	
	@EmbeddedId
	private GeoplRltspPK id;

	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;

	@Column(name="EXPIRATION_DT")
	private Date expirationDate;

	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;
	
	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByNm;

	//bi-directional many-to-one association to GeoplRltspType
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GEOPL_RLTSP_TYPE_CD", foreignKey=@ForeignKey(name="none"), insertable = false, updatable = false)
	private GeopoliticalRelationshipType geoplRltspType;

}