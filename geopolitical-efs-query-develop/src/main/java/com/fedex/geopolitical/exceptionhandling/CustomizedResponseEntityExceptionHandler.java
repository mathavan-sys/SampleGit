package com.fedex.geopolitical.exceptionhandling;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dtometa.DataDTO;
import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;
import com.fedex.geopolitical.dtoresponse.HolidayResponseDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.errorresponse.ApiError;
import com.fedex.geopolitical.errorresponse.ErrorDTO;
import com.fedex.geopolitical.errorresponse.ErrorMessages;
import com.fedex.geopolitical.exception.BigIntegerFormatException;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String COULD_NOT_BE_CONVERTED_TO_TYPE = "The parameter '%s' of value '%s' could not be converted to type '%s'";
	private static final String COULD_NOT_FIND_METHOD_FOR_URL = "Could not find the %s method for URL %s";
	private static final String VALIDATION_FAILED = "Validation Failed";
	private static final String NUMERIC_OUT_OF_BOUND = "numeric value out of bounds";
	private static final String FOR_FIELD = " for field ";
	private static final String SPLIT_CHAR = "\\.";
	private static final String EXCEPTION = "Exception";
	private static final String BIG_INTEGER_FORMAT_EXCEPTION = "Numeric Value Not Found";
	private static final String METHOD_ARGUMENT_MISMATCH_EXCEPTION = "Method Argument Mismatch";
	private static final String NO_HANDLER_FOUND_EXCEPTION = "Handler Not Found";
	private static final String REMOVE_CLASS_NAME = "com.fedex.geopolitical";
	private static final String ERROR_MESSAGE = "Request Submitted With Error";
	private static final String ERROR = "Error";
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		MetaDTO metaDTO = new MetaDTO();
		MessageDTO messageDTO = new MessageDTO();
		DataDTO dataDTO = new DataDTO();
		dataDTO.setErrorMessage(ERROR_MESSAGE);
		messageDTO.setStatus(ResponseStatus.ERROR);
		messageDTO.setInternalMessage(VALIDATION_FAILED);
		messageDTO.setData(dataDTO);
		metaDTO.setVersion(GenericConstants.VERSION);
		metaDTO.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaDTO.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		metaDTO.setMessage(messageDTO);
		List<ErrorDTO> errorDTOList = new ArrayList<ErrorDTO>();
		String errors = "";
		for (FieldError fe : fieldErrors) {
			if(fe.getDefaultMessage().contains(NUMERIC_OUT_OF_BOUND) && fe.getField().contains(".")){
				errors = errors + fe.getDefaultMessage() + FOR_FIELD + fe.getField().split(SPLIT_CHAR)[fe.getField().split(SPLIT_CHAR).length-1] + ", ";
			}else if(fe.getDefaultMessage().contains(REMOVE_CLASS_NAME)){
				continue;
			}else{
				errors = errors + fe.getDefaultMessage() + ", ";
			}
		}

		ApiError apiError = new ApiError(metaDTO, errorDTOList);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(BigIntegerFormatException.class)
	public final ResponseEntity<ApiError> handleLongFormatException(BigIntegerFormatException ex,
			final HttpServletRequest request) {

		MetaDTO metaDTO = new MetaDTO();
		MessageDTO messageDTO = new MessageDTO();
		DataDTO dataDTO = new DataDTO();
		dataDTO.setErrorMessage(ERROR_MESSAGE);
		messageDTO.setStatus(ResponseStatus.ERROR);
		messageDTO.setInternalMessage(BIG_INTEGER_FORMAT_EXCEPTION);
		messageDTO.setData(dataDTO);
		metaDTO.setVersion(GenericConstants.VERSION);
		metaDTO.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaDTO.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		metaDTO.setMessage(messageDTO);
		List<ErrorDTO> errorDTOList = new ArrayList<ErrorDTO>();
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setFieldName(ERROR);
		errorDTO.setMessage(ex.getMessage());
		errorDTOList.add(errorDTO);
		ApiError apiError = new ApiError(metaDTO, errorDTOList);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ApiError> handleException(Exception ex, final HttpServletRequest request) {

		MetaDTO metaDTO = new MetaDTO();
		MessageDTO messageDTO = new MessageDTO();
		DataDTO dataDTO = new DataDTO();
		dataDTO.setErrorMessage(ERROR_MESSAGE);
		messageDTO.setStatus(ResponseStatus.ERROR);
		messageDTO.setInternalMessage(EXCEPTION);
		messageDTO.setData(dataDTO);
		metaDTO.setVersion(GenericConstants.VERSION);
		metaDTO.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaDTO.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		metaDTO.setMessage(messageDTO);
		List<ErrorDTO> errorDTOList = new ArrayList<ErrorDTO>();
		ErrorDTO errorDTO = new ErrorDTO();
		if (ex.getMessage().contains("effectiveDate")) {
			errorDTO.setFieldName("effectiveDate");
			errorDTO.setMessage(
					ex.getMessage().split("effectiveDate")[ex.getMessage().split("effectiveDate").length - 1]);
		} else {
			errorDTO.setFieldName(ERROR);
			errorDTO.setMessage(ex.getMessage());
		}
		errorDTOList.add(errorDTO);
			
		ApiError apiError = new ApiError(metaDTO, errorDTOList);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		
		String message = String.format(CustomizedResponseEntityExceptionHandler.COULD_NOT_BE_CONVERTED_TO_TYPE,
			ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		MetaDTO metaDTO = new MetaDTO();
		MessageDTO messageDTO = new MessageDTO();
		DataDTO dataDTO = new DataDTO();
		dataDTO.setErrorMessage(ERROR_MESSAGE);
		messageDTO.setStatus(ResponseStatus.ERROR);
		messageDTO.setInternalMessage(METHOD_ARGUMENT_MISMATCH_EXCEPTION);
		messageDTO.setData(dataDTO);
		metaDTO.setVersion(GenericConstants.VERSION);
		metaDTO.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaDTO.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		metaDTO.setMessage(messageDTO);
		List<ErrorDTO> errorDTOList = new ArrayList<ErrorDTO>();
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setFieldName(ERROR);
		errorDTO.setMessage(message);
		errorDTOList.add(errorDTO);
		ApiError apiError = new ApiError(metaDTO, errorDTOList);
		return buildResponseEntity(apiError, HttpStatus.BAD_REQUEST);
	
	}


	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String message = String.format(CustomizedResponseEntityExceptionHandler.COULD_NOT_FIND_METHOD_FOR_URL,
			ex.getHttpMethod(), ex.getRequestURL());

		MetaDTO metaDTO = new MetaDTO();
		MessageDTO messageDTO = new MessageDTO();
		DataDTO dataDTO = new DataDTO();
		dataDTO.setErrorMessage(ERROR_MESSAGE);
		messageDTO.setStatus(ResponseStatus.ERROR);
		messageDTO.setInternalMessage(NO_HANDLER_FOUND_EXCEPTION);
		messageDTO.setData(dataDTO);
		metaDTO.setVersion(GenericConstants.VERSION);
		metaDTO.setTimeStamp(CommonUtility.getCurrentTimestampAsString());
		metaDTO.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		metaDTO.setMessage(messageDTO);
		List<ErrorDTO> errorDTOList = new ArrayList<ErrorDTO>();
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setFieldName(ERROR);
		errorDTO.setMessage(message);
		errorDTOList.add(errorDTO);
		ApiError apiError = new ApiError(metaDTO, errorDTOList);
		return buildResponseEntity(apiError, HttpStatus.BAD_REQUEST);
	
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError, HttpStatus status) {
		return new ResponseEntity<>(apiError, status);
	}
	
}
