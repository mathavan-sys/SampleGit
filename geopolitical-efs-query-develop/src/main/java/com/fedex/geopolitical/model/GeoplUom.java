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
 * The persistent class for the GEOPL_UOM database table.
 * 
 */
@Entity
@Data
@Table(name="GEOPL_UOM")
public class GeoplUom implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GeoplUomPK id;

	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;

	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByNm;

	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;
	
	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;
	
	@Column(name="EXPIRATION_DT")
	private Date expirationDate;

	//bi-directional many-to-one association to RefUomType
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="UOM_TYPE_CD", foreignKey=@ForeignKey(name="none"), insertable = false, updatable = false)
	private RefUomType refUomType;

}