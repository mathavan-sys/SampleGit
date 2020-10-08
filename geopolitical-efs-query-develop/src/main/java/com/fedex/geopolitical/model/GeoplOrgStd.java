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
 * The persistent class for the GEOPL_ORG_STD database table.
 * 
 */
@Entity
@Data
@Table(name="GEOPL_ORG_STD")
public class GeoplOrgStd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORG_STD_CD")
	private String orgStdCd;

	@Column(name="ORG_STD_NM")
	private String orgStdNm;
	
	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByUserId;

	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;
	
	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;
	
	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;

	
	//bi-directional many-to-one association to CntryOrgStd
	@OneToMany(mappedBy="geoplOrgStd", cascade = CascadeType.ALL)
	private Set<CntryOrgStd> cntryOrgStds;

	//bi-directional many-to-one association to StProvStd
	@OneToMany(mappedBy="geoplOrgStd", cascade = CascadeType.ALL)
	private Set<StProvStd> stProvStds;

}