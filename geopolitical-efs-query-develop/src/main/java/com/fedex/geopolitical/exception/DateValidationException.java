package com.fedex.geopolitical.exception;


public class DateValidationException extends RuntimeException{
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public DateValidationException() {
	        super();
	    }
	    public DateValidationException(String message, Throwable cause) {
	        super(message, cause);
	    }
	    public DateValidationException(String message) {
	        super(message);
	    }
	    public DateValidationException(Throwable cause) {
	        super(cause);
	    }
	}

