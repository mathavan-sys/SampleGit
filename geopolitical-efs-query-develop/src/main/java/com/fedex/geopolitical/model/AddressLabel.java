package com.fedex.geopolitical.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

/**
 * The persistent class for the ADDRESS database table.
 * 
 */
@Entity
@Data
@Table(name = "COUNTRY_ADDRESS_LABEL")
public class AddressLabel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AddressLabelPK id;

	@Column(name = "FULL_ADDR_LINE_LABEL_DESC")
	private String fullAddressLineLabelDescription;

	@Column(name = "BRAND_ADDR_LINE_LABEL_DESC")
	private String brandAddressLineLabelDescription;

	@Column(name = "APPL_FLG")
	private String applicableFlag;

	@Column(name = "EFFECTIVE_DT")
	private Date effectiveDate;

	@Column(name = "EXPIRATION_DT")
	private Date expirationDate;

	@Column(name = "CREATED_BY_USER_ID")
	private String createdByUserId;

	@Column(name = "CREATED_TMSTP")
	private Timestamp createdTmstp;

	@Column(name = "LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByNm;

	@Column(name = "LAST_UPDATED_TMSTP")
	private Timestamp lastUpdatedTmstp;

}