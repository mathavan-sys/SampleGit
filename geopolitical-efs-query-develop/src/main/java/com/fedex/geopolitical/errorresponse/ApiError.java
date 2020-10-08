package com.fedex.geopolitical.errorresponse;

import java.util.List;

import com.fedex.geopolitical.dtometa.MetaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

	private MetaDTO meta;
	private List<ErrorDTO> errors;
	
}
