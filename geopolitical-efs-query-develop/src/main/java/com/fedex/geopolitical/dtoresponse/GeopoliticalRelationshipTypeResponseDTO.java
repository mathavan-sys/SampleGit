package com.fedex.geopolitical.dtoresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GeopoliticalRelationshipTypeResponseDTO {
	
	private String geopoliticalRelationshipTypeCode;
	private String geopoliticalRelationshipTypeDescription;
	

}
