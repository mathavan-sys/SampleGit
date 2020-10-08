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
import lombok.ToString;


/**
 * The persistent class for the REF_UOM_TYPE database table.
 * 
 */
@Entity
@Data
@ToString
@Table(name="REF_UOM_TYPE")
public class RefUomType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="UOM_TYPE_CD")
	private String uomTypeCd;

	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;

	@Column(name="UOM_TYPE_DESC")
	private String uomTypeDesc;

	@Column(name="UOM_TYPE_NM")
	private String uomTypeNm;
	
	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByUserId;
	
	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;
	
	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;

	//bi-directional many-to-one association to GeoplUom
	@OneToMany(mappedBy="refUomType", cascade = CascadeType.ALL)
	private Set<GeoplUom> geoplUoms;

}