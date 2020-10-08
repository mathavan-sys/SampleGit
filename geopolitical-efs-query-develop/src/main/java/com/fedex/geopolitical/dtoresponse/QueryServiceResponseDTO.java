package com.fedex.geopolitical.dtoresponse;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fedex.geopolitical.dtometa.MetaDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QueryServiceResponse
 */
@Validated
@Data
@NoArgsConstructor
public class QueryServiceResponseDTO {
	@JsonProperty("meta")
	private MetaDTO meta;

	@JsonProperty("data")
	private Object data;

	public QueryServiceResponseDTO(QueryServiceResponseDTO copy) {
		this.setMeta(copy.getMeta());
		this.setData(copy.getData());
	}

}
