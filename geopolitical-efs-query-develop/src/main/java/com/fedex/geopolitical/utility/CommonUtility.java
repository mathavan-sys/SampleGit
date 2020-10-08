package com.fedex.geopolitical.utility;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dtometa.DataDTO;
import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;
import com.fedex.geopolitical.errorresponse.ApiError;
import com.fedex.geopolitical.errorresponse.ErrorDTO;
import com.fedex.geopolitical.exception.DateFormatException;

public class CommonUtility {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtility.class);
	public static final String DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	public static final String INVALID_USER = "Invalid User Details";

	private CommonUtility() {
	}

	public static Date getCurrenctDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;
		return java.sql.Date.valueOf(dtf.format(LocalDate.now()));
	}

	public static Date getDefaultExpirationDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;
		return java.sql.Date.valueOf(dtf.format(LocalDate.of(9999, 12, 31)));
	}

	public static Date checkValidDate(String date) throws JsonProcessingException {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		Pattern pattern = Pattern.compile(GenericConstants.REGEX_DATE_VALIDATION);
		if (pattern.matcher(date).matches()) {
			SimpleDateFormat sdf = new SimpleDateFormat(GenericConstants.DATE_FORMAT);
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				LOGGER.info(GenericConstants.NOT_PARSEABLE);
			}
		} else {
			throw new DateFormatException(GenericConstants.INVALID_DATE_FORMAT);
		}
		return null;
	}

	public static String getCurrentTimestampAsString() {
		DateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);
		return dateFormat.format(new Timestamp(System.currentTimeMillis()));
	}
	
	public static void populateSuccess_Status_StatusCd_TS(MessageDTO message,MetaDTO metaResponse) {
		message.setStatus(ResponseStatus.SUCCESS);
		metaResponse.setTimeStamp(getCurrentTimestampAsString());
		metaResponse.setStatusCode(GenericConstants.META_SUCCESS_STATUS_CODE);
	}

	public static void populateForbidden_Status_StatusCd_TS(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		MetaDTO metaDTO = new MetaDTO();
		MessageDTO messageDTO = new MessageDTO();
		DataDTO dataDTO = new DataDTO();
		dataDTO.setErrorMessage(GenericConstants.HTTPS_ERROR_MESSAGE);
		messageDTO.setStatus(ResponseStatus.ERROR);
		messageDTO.setData(dataDTO);
		metaDTO.setVersion(GenericConstants.VERSION);
		metaDTO.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaDTO.setStatusCode(String.valueOf(HttpServletResponse.SC_FORBIDDEN));
		metaDTO.setMessage(messageDTO);
		List<ErrorDTO> errorDTOList = new ArrayList<ErrorDTO>();
		ErrorDTO errorDTO = new ErrorDTO();
		messageDTO.setInternalMessage(INVALID_USER);
		errorDTO.setFieldName(GenericConstants.INVALID_PROTOCOL);
		errorDTOList.add(errorDTO);
		
		ApiError apiError = new ApiError(metaDTO, errorDTOList);
		
		response.setContentType(GenericConstants.APPLICATION_JSON);
		response.getOutputStream().print(MessageHelper.asJsonString(apiError));
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.flushBuffer();
	}
	
	public static String getCurrentTransactionId() {
		return MDC.get(GenericConstants.TRANSACTIONID);
	}
}
