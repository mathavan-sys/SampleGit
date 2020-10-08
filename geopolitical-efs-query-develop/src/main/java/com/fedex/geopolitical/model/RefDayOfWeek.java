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
 * The persistent class for the REF_DAY_OF_WEEK database table.
 * 
 */
@Entity
@Data
@Table(name="REF_DAY_OF_WEEK")
public class RefDayOfWeek implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DAY_OF_WEEK_NBR", updatable = false, nullable = false)
	private long dayOfWeekNumber;

	@Column(name="DAY_OF_WEEK_FULL_NM")
	private String dayOfWeekFullName;

	@Column(name="DAY_OF_WEEK_SHORT_NM")
	private String dayOfWeekShortName;

	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;
	
	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;
	
	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByUserId;
	
	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;
	
	//bi-directional many-to-one association to TrnslDow
	@OneToMany(mappedBy="refDayOfWeek", cascade = CascadeType.ALL)
	private Set<TrnslDow> trnslDows;

}