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
 * The persistent class for the GEOPL_HDAY database table.
 * 
 */
@Entity
@Data
@Table(name="GEOPL_HDAY")
public class GeoplHday implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GeoplHdayPK id;

	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;

	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;
	
	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;

	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByNm;

	@Column(name="EXPIRATION_DT")
	private Date expirationDate;

	//bi-directional many-to-one association to Holiday
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="HDAY_ID", foreignKey=@ForeignKey(name="none"), insertable = false, updatable = false)
	private Holiday holiday;

}