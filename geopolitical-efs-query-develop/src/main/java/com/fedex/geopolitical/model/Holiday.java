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
 * The persistent class for the HOLIDAY database table.
 * 
 */
@Entity
@Data
@Table(name="HOLIDAY")
public class Holiday implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HDAY_ID", updatable = false, nullable = false)
	private long holidayId;

	@Column(name="HDAY_DT_PARM_TXT")
	private String holidayDateParamText;

	@Column(name="HDAY_NM")
	private String holidayName;

	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByUserId;

	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;
	
	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;
	
	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;

	//bi-directional many-to-one association to GeoplHday
	@OneToMany(mappedBy="holiday", cascade = CascadeType.ALL)
	private Set<GeoplHday> geoplHdays;

}