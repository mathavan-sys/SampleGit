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
public class DateValidationExceptionTest {

	@InjectMocks
	private DateValidationException dateValidationException;

	@Mock
	private Throwable throwable;

	@Test
	public void DateValidationTest() {
		throwable = new DateValidationException("nullpointer Exception");
		Throwable cause = throwable.getCause();
		Assert.assertEquals("nullpointer Exception", throwable.getMessage());

		throwable = new DateValidationException("nullpointer Exception", cause);
		Assert.assertEquals("nullpointer Exception", throwable.getMessage());

		throwable = new DateValidationException();
		Assert.assertEquals(null, throwable.getMessage());

		throwable = new DateValidationException(cause);
		Assert.assertEquals(cause, throwable.getCause());

	}

}
