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
public class LongFormatExceptionTest {

	@InjectMocks
	private LongFormatException longFormatException;

	@Mock
	private Throwable throwable;

	@Test
	public void longFormatTest() {
		throwable = new LongFormatException("nullpointer Exception");
		Throwable cause = throwable.getCause();
		Assert.assertEquals("nullpointer Exception", throwable.getMessage());

		throwable = new LongFormatException("nullpointer Exception", cause);
		Assert.assertEquals("nullpointer Exception", throwable.getMessage());

		throwable = new LongFormatException();
		Assert.assertEquals(null, throwable.getMessage());

		throwable = new LongFormatException(cause);
		Assert.assertEquals(cause, throwable.getCause());

	}

}
