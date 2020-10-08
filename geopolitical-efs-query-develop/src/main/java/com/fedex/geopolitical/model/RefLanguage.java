package com.fedex.geopolitical.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The persistent class for the REF_LANGUAGE database table.
 * 
 */
@Entity
@Data
@ToString(exclude = { "locales" })
@EqualsAndHashCode(exclude = { "locales" })
@Table(name = "LANGUAGE")
public class RefLanguage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "LANGUAGE_CD")
	private String langCd;

	@Column(name = "CREATED_BY_USER_ID")
	private String createdByUserId;

	@Column(name = "CREATED_TMSTP")
	private Timestamp createdTmstp;

	@Column(name = "LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByUserId;

	@Column(name = "LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;

	@Column(name = "NATIVE_SCRIPT_LANGUAGE_NM")
	private String nativeScriptLanguageNm;

	@Column(name = "ENGL_LANGUAGE_NM")
	private String engLanguageNm;

	@Column(name = "NATV_SCRIPT_CD")
	private String nativeScrptCd;
	
	// bi-directional many-to-one association to Locale
	@OneToMany(mappedBy = "refLanguage", cascade = CascadeType.ALL)
	private List<Locale> locales;

}