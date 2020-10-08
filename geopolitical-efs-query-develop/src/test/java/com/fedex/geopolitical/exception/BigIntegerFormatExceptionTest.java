package com.fedex.geopolitical.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class BigIntegerFormatExceptionTest {

	@InjectMocks
	private BigIntegerFormatException bigIntegerFormatException;
	
	@Mock
	private Throwable throwable;
	
	@Test
	public void BigIntegerFormatTest()
	{
		throwable = new BigIntegerFormatException("nullpointer Exception");
		Throwable cause= throwable.getCause();
		Assert.assertEquals("nullpointer Exception", throwable.getMessage());
		
		throwable = new BigIntegerFormatException("nullpointer Exception", cause);
		Assert.assertEquals("nullpointer Exception", throwable.getMessage());
		
		
		throwable = new BigIntegerFormatException();
		Assert.assertEquals(null, throwable.getMessage());
		
		throwable = new BigIntegerFormatException(cause);
		Assert.assertEquals(cause, throwable.getCause());
		
	}
}
