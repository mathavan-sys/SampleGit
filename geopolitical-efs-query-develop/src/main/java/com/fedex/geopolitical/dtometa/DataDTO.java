package com.fedex.geopolitical.dtometa;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String errorMessage;

}
