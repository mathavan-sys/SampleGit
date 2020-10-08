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
 * The persistent class for the GEOPL_AFFIL_TYPE database table.
 * 
 */
@Entity
@Data
@Table(name="GEOPL_AFFIL_TYPE")
public class GeoplAffilType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="AFFIL_TYPE_ID")
	private long affilTypeId;

	@Column(name="AFFIL_TYPE_CD")
	private String affilTypeCode;

	@Column(name="AFFIL_TYPE_NM")
	private String affilTypeName;

	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;

	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;

	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByUserId;

	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;
	
	@OneToMany(mappedBy="geoplAffilType", cascade = CascadeType.ALL)
	private Set<GeoplAffil> geoplAffils;

}