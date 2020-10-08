package com.fedex.geopolitical.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The persistent class for the TRNSL_MTH_OF_YR database table.
 * 
 */
@Entity
@Data
@Table(name = "TRNSL_MTH_OF_YR")
@ToString(exclude = { "refMonthOfYear" })
@EqualsAndHashCode(exclude = { "refMonthOfYear" })
public class TrnslMthOfYr implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TrnslMthOfYrPK id;

	@Column(name = "CREATED_BY_USER_ID")
	private String createdByUserId;

	@Column(name = "CREATED_TMSTP")
	private Timestamp createdTmstp;

	@Column(name = "LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByNm;

	@Column(name = "LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;

	@Column(name = "TRNSL_MONTH_OF_YEAR_NM")
	private String trnslMthOfYrNm;

	// bi-directional many-to-one association to RefMonthOfYear
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MONTH_OF_YEAR_NBR", foreignKey = @ForeignKey(name = "none"), insertable = false, updatable = false)
	private RefMonthOfYear refMonthOfYear;

}