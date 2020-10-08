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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;


/**
 * The persistent class for the GEOPOLITICAL database table.
 * 
 */
@Entity
@Data
@Table(name="GEOPOLITICAL")
public class Geopolitical implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private GeopoliticalPK id;

	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;

	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;

	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByNm;

	@Column(name="EXPIRATION_DT")
	private Date expirationDate;

	//bi-directional many-to-one association to GeoplType
	@Column(name="GEOPL_TYPE_ID")
	private long geopoliticalTypeId;
	
	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "GEOPL_TYPE_ID", foreignKey=@ForeignKey(name="none") , insertable = false, updatable = false)
	private GeopoliticalType geopoliticalType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "GEOPL_ID", referencedColumnName = "GEOPL_ID", foreignKey=@ForeignKey(name="none") , insertable = false, updatable = false),
		@JoinColumn(name = "EFFECTIVE_DT", referencedColumnName = "EFFECTIVE_DT", foreignKey=@ForeignKey(name="none") , insertable = false, updatable = false)
	})
	private Country country;

}