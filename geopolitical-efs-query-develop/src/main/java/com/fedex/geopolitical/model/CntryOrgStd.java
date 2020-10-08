package com.fedex.geopolitical.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;


/**
 * The persistent class for the CNTRY_ORG_STD database table.
 * 
 */
@Entity
@Data
@Table(name="CNTRY_ORG_STD")
public class CntryOrgStd implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CntryOrgStdPK id;

	@Column(name="CNTRY_FULL_NM")
	private String cntryFullNm;

	@Column(name="EXPIRATION_DT")
	private Date expirationDate;
	
	@Column(name="CNTRY_SHT_NM")
	private String cntryShtNm;
	
	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;

	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;

	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByNm;

	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;

	//bi-directional many-to-one association to GeoplOrgStd
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ORG_STD_CD", foreignKey=@ForeignKey(name="none"), insertable = false, updatable = false)
	private GeoplOrgStd geoplOrgStd;

}