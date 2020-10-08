package com.fedex.geopolitical.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the GEOPL_RLTSP database table.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class GeoplRltspPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="GEOPL_COMPT_ID")
	private String geoplComptId;

	@Column(name="RELTD_GEOPL_COMPT_ID")
	private String reltdGeoplComptId;

	@Column(name="GEOPL_RLTSP_TYPE_CD")
	private String geoplRltspTypeCd;
	
	@Column(name="EFFECTIVE_DT")
	private Date effectiveDate;

}