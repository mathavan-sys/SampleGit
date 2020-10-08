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
 * The persistent class for the REF_MONTH_OF_YEAR database table.
 * 
 */
@Entity
@Data
@Table(name="REF_MONTH_OF_YEAR")
public class RefMonthOfYear implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="MONTH_OF_YEAR_NBR", updatable = false, nullable = false)
	private long monthOfYearNumber;

	@Column(name="MONTH_OF_YEAR_SHT_NM")
	private String monthOfYearShortName;
	
	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;
	
	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;
	
	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByUserId;
	
	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;
	
	//bi-directional many-to-one association to TrnslMthOfYr
	@OneToMany(mappedBy="refMonthOfYear", cascade = CascadeType.ALL)
	private Set<TrnslMthOfYr> trnslMthOfYrs;

}