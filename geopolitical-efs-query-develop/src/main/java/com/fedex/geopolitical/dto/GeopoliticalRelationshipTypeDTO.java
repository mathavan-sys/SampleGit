package com.fedex.geopolitical.dto;

import lombok.Data;

@Data
public class GeopoliticalRelationshipTypeDTO {
	
	private String geopoliticalRelationshipTypeCode;
	private String areaRelationshipTypeDescription;
	private User user;

}
