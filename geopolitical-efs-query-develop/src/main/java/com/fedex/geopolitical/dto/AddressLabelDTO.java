package com.fedex.geopolitical.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class AddressLabelDTO {

	@JsonInclude(Include.NON_NULL)
	private String geopoliticalId;
	private String brandAddressLineDescription;
	private int addressLineNumber;
	private boolean applicable;

}
