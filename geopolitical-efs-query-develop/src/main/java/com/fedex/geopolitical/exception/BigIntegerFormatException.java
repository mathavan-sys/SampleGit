package com.fedex.geopolitical.exception;


public class BigIntegerFormatException extends RuntimeException{
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public BigIntegerFormatException() {
	        super();
	    }
	    public BigIntegerFormatException(String message, Throwable cause) {
	        super(message, cause);
	    }
	    public BigIntegerFormatException(String message) {
	        super(message);
	    }
	    public BigIntegerFormatException(Throwable cause) {
	        super(cause);
	    }
	}

