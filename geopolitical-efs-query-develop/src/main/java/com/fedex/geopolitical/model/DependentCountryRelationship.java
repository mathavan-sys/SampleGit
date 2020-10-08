package com.fedex.geopolitical.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


/**
 * The persistent class for the DEPN_CNTRY_RLTSP database table.
 * 
 */
@Entity
@Data
@Table(name="DEPN_CNTRY_RLTSP")
public class DependentCountryRelationship implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DEPN_RLTSP_ID", updatable = false, nullable = false)
	private String dependentRelationshipId;

	@Column(name="DEPN_RLTSP_DESC")
	private String dependentRelationshipDescription;

	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;
	
	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByUserId;
	
	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;
	
	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;
	
}