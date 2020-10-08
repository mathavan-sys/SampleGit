package com.fedex.geopolitical.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the ADDRESS database table.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AddressLabelPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "GEOPL_ID")
	private String geopoliticalId;

	@Column(name = "ADDR_LINE_NBR")
	private int addressLineNumber;

	@Column(name = "LOCL_CD")
	private String loclcode;

}