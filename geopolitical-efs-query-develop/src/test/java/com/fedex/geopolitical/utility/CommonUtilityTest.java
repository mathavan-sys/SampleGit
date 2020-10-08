package com.fedex.geopolitical.utility;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;

public class CommonUtilityTest {
	private static long value;
	private static Random random;

	private CommonUtilityTest(){}


	public static long generateId(){
		if(random==null){
			random = new Random();
		}
		value = -1;
		while (value <= 0) {
			value = random.nextLong();
		}
		return value;
	} 

	public static Timestamp getCurrentTimeStamp(){
		return new Timestamp(Instant.now().toEpochMilli());
	}
	
	public static String getCurrenctDate(){
		DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;
		return dtf.format(LocalDate.now());
	}
	
	public static Date getDefaultExpirationDate(){
			DateTime dateTime = new DateTime(9999, 12, 31, 0, 0);
			return dateTime.toDate();
	}
	

	public static String getCurrentMethodName() {

		return Thread.currentThread().getStackTrace()[2].getClassName() + "."
				+ Thread.currentThread().getStackTrace()[2].getMethodName();
	}
	
	
}
