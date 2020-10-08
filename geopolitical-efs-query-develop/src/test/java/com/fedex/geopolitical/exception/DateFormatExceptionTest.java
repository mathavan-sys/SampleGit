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
public class DateFormatExceptionTest {

	@InjectMocks
	private DateFormatException dateFormatException;

	@Mock
	private Throwable throwable;

	@Test
	public void DateFormatTest() {
		throwable = new DateFormatException("nullpointer Exception");
		Throwable cause = throwable.getCause();
		Assert.assertEquals("nullpointer Exception", throwable.getMessage());

		throwable = new DateFormatException("nullpointer Exception", cause);
		Assert.assertEquals("nullpointer Exception", throwable.getMessage());

		throwable = new DateFormatException();
		Assert.assertEquals(null, throwable.getMessage());

		throwable = new DateFormatException(cause);
		Assert.assertEquals(cause, throwable.getCause());

	}

}
