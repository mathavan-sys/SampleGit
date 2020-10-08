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
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * The persistent class for the LOCALE database table.
 * 
 */
@Entity
@Data
@Table(name="LOCALE")
@ToString(exclude={"refLanguage"})
@EqualsAndHashCode(exclude={"refLanguage"})
public class Locale implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LocalePK id;
	
	@Column(name="GEOPL_ID")
	private String geopoliticalId;
	
	@Column(name="LANGUAGE_CD")
	private String langCd;
	
	@Column(name="CLDR_VERS_DT")
	private Date cldrVersionDate;

	@Column(name="CLDR_VERS_NBR")
	private String cldrVersionNumber;

	@Column(name="CREATED_BY_USER_ID")
	private String createdByUserId;

	@Column(name="CREATED_TMSTP")
	private Timestamp createdTmstp;	

	@Column(name="DT_FULL_FORMT_DESC")
	private String dateFullFormatDescription;

	@Column(name="DT_LONG_FORMT_DESC")
	private String dateLongFormatDescription;

	@Column(name="DT_MED_FORMT_DESC")
	private String dateMediumFormatDescription;

	@Column(name="DT_SHT_FORMT_DESC")
	private String dateShortFormatDescription;

	@Column(name="LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByNm;
	
	@Column(name="LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;
	
	@Column(name="EXPIRATION_DT")
	private Date expirationDate;
	
	//bi-directional many-to-one association to RefLanguage
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="LANGUAGE_CD", foreignKey=@ForeignKey(name="none"), insertable = false, updatable = false)
	private RefLanguage refLanguage;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SCRIPT_CD", foreignKey=@ForeignKey(name="none"))
	private RefScript refScript;

}