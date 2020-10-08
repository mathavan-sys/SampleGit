package com.fedex.geopolitical.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;


/**
 * The persistent class for the GEOPL_TYPE database table.
 * 
 */
@Entity
@Data
@Table(name="GEOPL_TYPE")
public class GeopoliticalType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="GEOPL_TYPE_ID", updatable = false, nullable = false)
	private long geopoliticalTypeId;

	@Column(name="GEOPL_TYPE_NM")
	private String geopoliticalTypeName;
	
	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByUserId;

	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;
	
	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;
	
	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "geopoliticalType")
	private List<Geopolitical> geopoliticals;

}