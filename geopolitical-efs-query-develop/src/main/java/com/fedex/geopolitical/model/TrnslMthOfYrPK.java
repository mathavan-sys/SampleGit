package com.fedex.geopolitical.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the TRNSL_MTH_OF_YR database table.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TrnslMthOfYrPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="MONTH_OF_YEAR_NBR")
	private long mthOfYrNbr;

	@Column(name="LOCL_CD")
	private String loclcode;

}