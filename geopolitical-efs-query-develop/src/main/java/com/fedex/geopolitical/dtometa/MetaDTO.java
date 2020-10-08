package com.fedex.geopolitical.dtometa;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fedex.geopolitical.utility.CommonUtility;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MetaResponse
 */
@Validated
@Data
@NoArgsConstructor
public class MetaDTO   {
	
	@JsonProperty("version")
	private String version;
	
	@JsonProperty("transactionId")
	private String transactionId;
	
	@JsonProperty("timeStamp")
	private String timeStamp;
	
	@JsonProperty("statusCode")
	private String statusCode;

	@JsonProperty("message")
	private MessageDTO message;;
	
	//Getting current transaction id from MDC
	public String getTransactionId() {
		return CommonUtility.getCurrentTransactionId();
	}

}
