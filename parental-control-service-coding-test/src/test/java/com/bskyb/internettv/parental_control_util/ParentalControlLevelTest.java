package com.bskyb.internettv.parental_control_util;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import com.bskyb.internettv.parental_control_exception.ParentalControlLevelException;

public class ParentalControlLevelTest {

	@Test
	public void shouldGetTheLevelFromTheName() throws ParentalControlLevelException {
		assertEquals(ParentalControlLevel.LEVEL_U, ParentalControlLevel.getByName("U"));
		assertEquals(ParentalControlLevel.LEVEL_PG, ParentalControlLevel.getByName("PG"));
		assertEquals(ParentalControlLevel.LEVEL_12, ParentalControlLevel.getByName("12"));
		assertEquals(ParentalControlLevel.LEVEL_15, ParentalControlLevel.getByName("15"));
		assertEquals(ParentalControlLevel.LEVEL_18, ParentalControlLevel.getByName("18"));
	}
	
	@Test(expected = ParentalControlLevelException.class)
	public void shouldThrowExceptionWithLevelUnknown() throws ParentalControlLevelException {
		ParentalControlLevel.getByName("Name for test!" + Calendar.getInstance().toString());
	}
	
	@Test(expected = ParentalControlLevelException.class)
	public void shouldThrowExceptionWithLevelNull() throws ParentalControlLevelException {
		ParentalControlLevel.getByName(null);
	}

}
