package com.fedex.geopolitical.dtometa;

import java.io.Serializable;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fedex.geopolitical.utility.ResponseStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Message
 */
@Validated
@Data
@NoArgsConstructor
public class MessageDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private ResponseStatus status;
	private String internalMessage;
	private DataDTO data;

}
