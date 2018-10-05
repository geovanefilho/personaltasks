package com.generic.retailer.service.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.generic.retailer.model.Discount;
import com.generic.retailer.model.enums.DiscountAvailability;
import com.generic.retailer.model.enums.DiscountType;
import com.generic.retailer.service.DiscountService;

public class DiscountServiceImplTest {

	private static final int SCALE_2 = 2;
	
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	private static Discount availableLessHour;
	private static Discount availableHigherHour;
	private static Discount availableDayMonth;
	private static Discount availableDayWeek;
	private static Discount availableMonth;
	private static Discount availableWithoutAvailability;
	private static Discount unavailableDayWeek;
	private static Collection<Discount> discountsHourDay;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Integer hourEarlier = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + 1;
		availableLessHour = new Discount("5% off", DiscountType.PERCENTAGE, 1, new BigDecimal(5), DiscountAvailability.BEFORE_TIME, hourEarlier);
		availableLessHour.setId(1l);
		
		Integer hourLater = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) - 1;
		availableHigherHour = new Discount("7% off", DiscountType.PERCENTAGE, 2, new BigDecimal(7), DiscountAvailability.AFTER_TIME, hourLater);
		availableHigherHour.setId(2l);
		
		availableDayMonth = new Discount("10 Pounds off", DiscountType.PRICE, 3, new BigDecimal(10), DiscountAvailability.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		availableDayMonth.setId(3l);
		
		discountsHourDay = new ArrayList<Discount>();
		discountsHourDay.add(availableLessHour);
		discountsHourDay.add(availableHigherHour);
		discountsHourDay.add(availableDayMonth);
		
		availableDayWeek = new Discount("50 Pounds off", DiscountType.PRICE, 2, new BigDecimal(50), DiscountAvailability.DAY_OF_WEEK, Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
		availableDayWeek.setId(4l);
		
		availableMonth = new Discount("20% off", DiscountType.PERCENTAGE, 1, new BigDecimal(20), DiscountAvailability.MONTH_OF_YEAR, Calendar.getInstance().get(Calendar.MONTH));
		availableMonth.setId(5l);
		
		availableWithoutAvailability = new Discount("25% off", DiscountType.PERCENTAGE, 1, new BigDecimal(25));
		availableWithoutAvailability.setId(6l);
		
		Integer weekDayTomorrow = (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + 1);
		unavailableDayWeek = new Discount("Two for one", DiscountType.PERCENTAGE, 2, new BigDecimal(50), DiscountAvailability.DAY_OF_WEEK, weekDayTomorrow);
		unavailableDayWeek.setId(7l);
		
		
	}
	
	@Test
	public void isAvailableTrueWithoutAvailabilityTest() {
		DiscountService discountService = new DiscountServiceImpl();
		
		assertTrue(discountService.isAvailable(null, availableWithoutAvailability));
	}
	
	@Test
	public void isAvailableTrueWithAvailabilityInHourEarlierTest() {
		DiscountService discountService = new DiscountServiceImpl();
		
		assertTrue(discountService.isAvailable(null, availableLessHour));
	}
	
	@Test
	public void isAvailableTrueWithAvailabilityInHourLaterTest() {
		DiscountService discountService = new DiscountServiceImpl();
		
		assertTrue(discountService.isAvailable(null, availableHigherHour));
	}
	
	@Test
	public void isAvailableTrueWithAvailabilityInDayOfMonthTest() {
		DiscountService discountService = new DiscountServiceImpl();
		
		assertTrue(discountService.isAvailable(null, availableDayMonth));
	}
	
	@Test
	public void isAvailableTrueWithAvailabilityInDayOfWeekTest() {
		DiscountService discountService = new DiscountServiceImpl();
		
		assertTrue(discountService.isAvailable(null, availableDayWeek));
	}
	
	@Test
	public void isAvailableTrueWithAvailabilityInMonthTest() {
		DiscountService discountService = new DiscountServiceImpl();
		
		assertTrue(discountService.isAvailable(null, availableMonth));
	}
	
	@Test
	public void isAvailableNullTest() {
		DiscountService discountService = new DiscountServiceImpl();
		
		assertFalse(discountService.isAvailable(null, null));
	}
	
	@Test
	public void isUnavailableDayWeekTest() {
		DiscountService discountService = new DiscountServiceImpl();
		
		assertFalse(discountService.isAvailable(null, unavailableDayWeek));
	}
	
	@Test
	public void calculateDiscountValuesTest() {
		DiscountService discountService = new DiscountServiceImpl();
		
		Map<Discount, BigDecimal> values = new HashMap<Discount, BigDecimal>();
		values.put(availableLessHour, new BigDecimal(45.465).setScale(SCALE_2, RoundingMode.HALF_UP));
		values.put(availableHigherHour, new BigDecimal(54.558).setScale(SCALE_2, RoundingMode.HALF_UP));
		values.put(availableDayMonth, new BigDecimal(20.000).setScale(SCALE_2, RoundingMode.HALF_UP));
		
		Map<Discount, BigDecimal> result = discountService.calculateDiscountValues(null, discountsHourDay, new BigDecimal(7), new BigDecimal(129.90));
		
		
		assertArrayEquals(values.keySet().toArray(), result.keySet().toArray());
		assertArrayEquals(values.values().toArray(), result.values().toArray());
	}

}
