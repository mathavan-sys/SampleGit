package com.fedex.geopolitical.exceptionhandling;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dtometa.DataDTO;
import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;
import com.fedex.geopolitical.errorresponse.ApiError;
import com.fedex.geopolitical.errorresponse.ErrorDTO;
import com.fedex.geopolitical.errorresponse.ErrorMessages;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@ControllerAdvice
public class CustomControllerAdvise {

	private static final String METHOD_NOT_SUPPORTED_EXCEPTION = "No Such API Exists";
	// Added ACCESS_DENIED & INVALID_USER Constants for Redux Authorization
	// implementation.
	public static final String ACCESS_DENIED = "User is not authorized to access this API.";
	public static final String INVALID_USER = "Invalid User Details";
	private static final String ERROR_MESSAGE = "Request Submitted With Error";
	private static final String ERROR = "Error";
	private static final String COULD_NOT_FIND_METHOD_FOR_URL = "Could not find the %s method for URL %s";
	private static final String EXCEPTION = "Exception";

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public final ResponseEntity<ApiError> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException ex, final HttpServletRequest request) {

		MetaDTO metaDTO = new MetaDTO();
		MessageDTO messageDTO = new MessageDTO();
		DataDTO dataDTO = new DataDTO();
		dataDTO.setErrorMessage(ERROR_MESSAGE);
		messageDTO.setStatus(ResponseStatus.ERROR);
		messageDTO.setData(dataDTO);
		metaDTO.setVersion(GenericConstants.VERSION);
		metaDTO.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaDTO.setStatusCode(String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()));
		metaDTO.setMessage(messageDTO);
		List<ErrorDTO> errorDTOList = new ArrayList<ErrorDTO>();
		ErrorDTO errorDTO = new ErrorDTO();
		messageDTO.setInternalMessage(METHOD_NOT_SUPPORTED_EXCEPTION);
		errorDTO.setFieldName(ERROR);
		errorDTO.setMessage(ex.getMessage());
		errorDTOList.add(errorDTO);
		ApiError apiError = new ApiError(metaDTO, errorDTOList);

		return new ResponseEntity<>(apiError, HttpStatus.METHOD_NOT_ALLOWED);
	
	}

	// handleAccessDeniedException - to handle Access denied exception.
	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex) {

		MetaDTO metaDTO = new MetaDTO();
		MessageDTO messageDTO = new MessageDTO();
		DataDTO dataDTO = new DataDTO();
		dataDTO.setErrorMessage(ERROR_MESSAGE);
		messageDTO.setStatus(ResponseStatus.ERROR);
		messageDTO.setData(dataDTO);
		metaDTO.setVersion(GenericConstants.VERSION);
		metaDTO.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaDTO.setStatusCode(String.valueOf(HttpStatus.FORBIDDEN.value()));
		metaDTO.setMessage(messageDTO);
		List<ErrorDTO> errorDTOList = new ArrayList<ErrorDTO>();
		ErrorDTO errorDTO = new ErrorDTO();
		messageDTO.setInternalMessage(ACCESS_DENIED);
		errorDTO.setFieldName(ERROR);
		errorDTO.setMessage(ex.getMessage());
		errorDTOList.add(errorDTO);
		ApiError apiError = new ApiError(metaDTO, errorDTOList);
		return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
	
	}

	// handleAccessDeniedException - to handle Invalid user exception.
	@ExceptionHandler(UsernameNotFoundException.class)
	public final ResponseEntity<ApiError> handleUsernameNotFoundException(UsernameNotFoundException ex) {

		MetaDTO metaDTO = new MetaDTO();
		MessageDTO messageDTO = new MessageDTO();
		DataDTO dataDTO = new DataDTO();
		dataDTO.setErrorMessage(ERROR_MESSAGE);
		messageDTO.setStatus(ResponseStatus.ERROR);
		messageDTO.setData(dataDTO);
		metaDTO.setVersion(GenericConstants.VERSION);
		metaDTO.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaDTO.setStatusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
		metaDTO.setMessage(messageDTO);
		List<ErrorDTO> errorDTOList = new ArrayList<ErrorDTO>();
		ErrorDTO errorDTO = new ErrorDTO();
		messageDTO.setInternalMessage(INVALID_USER);
		errorDTO.setFieldName(ERROR);
		errorDTO.setMessage(ex.getMessage());
		errorDTOList.add(errorDTO);
		
		ApiError apiError = new ApiError(metaDTO, errorDTOList);
		return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
	
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex) {
		String message = String.format(COULD_NOT_FIND_METHOD_FOR_URL, ex.getHttpMethod(), ex.getRequestURL());

		MetaDTO metaDTO = new MetaDTO();
		MessageDTO messageDTO = new MessageDTO();
		DataDTO dataDTO = new DataDTO();
		dataDTO.setErrorMessage(ERROR_MESSAGE);
		messageDTO.setStatus(ResponseStatus.ERROR);
		messageDTO.setInternalMessage(EXCEPTION);
		messageDTO.setData(dataDTO);
		metaDTO.setVersion(GenericConstants.VERSION);
		metaDTO.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaDTO.setStatusCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
		metaDTO.setMessage(messageDTO);
		List<ErrorDTO> errorDTOList = new ArrayList<ErrorDTO>();
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setFieldName(ERROR);
		errorDTO.setMessage(message);
		errorDTOList.add(errorDTO);
		ApiError apiError = new ApiError(metaDTO, errorDTOList);
		return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
	}
}