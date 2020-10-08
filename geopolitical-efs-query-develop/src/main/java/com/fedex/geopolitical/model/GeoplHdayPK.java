package com.fedex.geopolitical.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the GEOPL_HDAY database table.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class GeoplHdayPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="GEOPL_ID")
	private String geopoliticalId;

	@Column(name="HDAY_ID")
	private long holidayId;
	
	@Column(name="EFFECTIVE_DT")
	private Date effectiveDate;

}