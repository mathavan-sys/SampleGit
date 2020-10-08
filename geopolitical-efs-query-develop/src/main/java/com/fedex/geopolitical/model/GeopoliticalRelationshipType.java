package com.fedex.geopolitical.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;


/**
 * The persistent class for the GEOPL_RLTSP_TYPE database table.
 * 
 */
@Entity
@Data
@Table(name="GEOPL_RLTSP_TYPE")
public class GeopoliticalRelationshipType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="GEOPL_RLTSP_TYPE_CD")
	private String geopoliticalRelationshipTypeCd;

	@Column(name="AREA_RLTSP_TYPE_DESC")
	private String areaRelationshipTypeDescription;

	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;

	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;

	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByNm;

	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;
	
	//bi-directional many-to-one association to GeoplRltsp
	@OneToMany(mappedBy="geoplRltspType", cascade = CascadeType.ALL)
	private Set<GeoplRltsp> geoplRltsps;

}