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
 * The persistent class for the TRNSL_DOW database table.
 * 
 */
@Entity
@Data
@Table(name = "TRNSL_DOW")
@ToString(exclude = { "refDayOfWeek" })
@EqualsAndHashCode(exclude = { "refDayOfWeek" })
public class TrnslDow implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TrnslDowPK id;

	@Column(name = "CREATED_BY_USER_ID")
	private String createdByUserId;

	@Column(name = "CREATED_TMSTP")
	private Timestamp createdTmstp;

	@Column(name = "LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByNm;

	@Column(name = "LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;

	@Column(name = "TRNSL_DOW_NM")
	private String trnslDowNm;

	// bi-directional many-to-one association to RefDayOfWeek
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DAY_OF_WEEK_NBR", foreignKey = @ForeignKey(name = "none"), insertable = false, updatable = false)
	private RefDayOfWeek refDayOfWeek;

}