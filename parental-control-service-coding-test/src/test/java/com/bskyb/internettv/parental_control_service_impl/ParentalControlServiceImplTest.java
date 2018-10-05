package com.bskyb.internettv.parental_control_service_impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.bskyb.internettv.parental_control_exception.Solution;
import com.bskyb.internettv.parental_control_service.ParentalControlService;
import com.bskyb.internettv.parental_control_util.ParentalControlLevel;
import com.bskyb.internettv.thirdparty.MovieService;
import com.bskyb.internettv.thirdparty.TechnicalFailureException;
import com.bskyb.internettv.thirdparty.TitleNotFoundException;

public class ParentalControlServiceImplTest {

	private static final String TITLE_ID = "TITLE_ID";

	private static ParentalControlService parentalControlService;
	
	@Mock
	private static MovieService movieServiceMock;
	
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Test
	public void whenCustomerSetUShouldSeeMovieWithUParentalControlLevel() throws Exception {
		parentalControlService = new ParentalControlServiceImpl(movieServiceMock);
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("U");
		
		Solution sol = new Solution();
		sol.printDiamonds(4, 1);
	}
	
	@Test
	public void whenCustomerSetUShouldNotSeeMovieWithHigherParentalControlLevel() throws Exception {
		parentalControlService = new ParentalControlServiceImpl(movieServiceMock);
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("PG");
		assertFalse(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_U.description(), TITLE_ID));
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("12");
		assertFalse(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_U.description(), TITLE_ID));
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("15");
		assertFalse(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_U.description(), TITLE_ID));
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("18");
		assertFalse(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_U.description(), TITLE_ID));
	}
	
	@Test
	public void whenCustomerSetPGShouldSeeMovieWithPGAndLowerParentalControlLevel() throws Exception {
		parentalControlService = new ParentalControlServiceImpl(movieServiceMock);
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("U");
		assertTrue(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_PG.description(), TITLE_ID));
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("PG");
		assertTrue(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_PG.description(), TITLE_ID));
	}
	
	@Test
	public void whenCustomerSetPGShouldNotSeeMovieWithHigherParentalControlLevel() throws Exception {
		parentalControlService = new ParentalControlServiceImpl(movieServiceMock);
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("12");
		assertFalse(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_PG.description(), TITLE_ID));
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("15");
		assertFalse(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_PG.description(), TITLE_ID));
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("18");
		assertFalse(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_PG.description(), TITLE_ID));
	}
	
	@Test
	public void whenCustomerSet12ShouldSeeMovieWith12AndLowerParentalControlLevel() throws Exception {
		parentalControlService = new ParentalControlServiceImpl(movieServiceMock);
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("U");
		assertTrue(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_12.description(), TITLE_ID));
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("PG");
		assertTrue(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_12.description(), TITLE_ID));
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("12");
		assertTrue(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_12.description(), TITLE_ID));
	}
	
	@Test
	public void whenCustomerSet12ShouldNotSeeMovieWithHigherParentalControlLevel() throws Exception {
		parentalControlService = new ParentalControlServiceImpl(movieServiceMock);
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("15");
		assertFalse(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_12.description(), TITLE_ID));
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("18");
		assertFalse(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_12.description(), TITLE_ID));
	}
	
	@Test
	public void whenCustomerSet15ShouldSeeMovieWith15AndLowerParentalControlLevel() throws Exception {
		parentalControlService = new ParentalControlServiceImpl(movieServiceMock);
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("U");
		assertTrue(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_15.description(), TITLE_ID));
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("PG");
		assertTrue(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_15.description(), TITLE_ID));
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("12");
		assertTrue(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_15.description(), TITLE_ID));
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("15");
		assertTrue(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_15.description(), TITLE_ID));
	}
	
	@Test
	public void whenCustomerSet15ShouldNotSeeMovieWithHigherParentalControlLevel() throws Exception {
		parentalControlService = new ParentalControlServiceImpl(movieServiceMock);
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("18");
		assertFalse(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_15.description(), TITLE_ID));
	}
	
	@Test
	public void whenCustomerSet18ShouldSeeMovieWithAnyParentalControlLevel() throws Exception {
		parentalControlService = new ParentalControlServiceImpl(movieServiceMock);
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("U");
		assertTrue(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_18.description(), TITLE_ID));
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("PG");
		assertTrue(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_18.description(), TITLE_ID));
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("12");
		assertTrue(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_18.description(), TITLE_ID));
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("15");
		assertTrue(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_18.description(), TITLE_ID));
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("18");
		assertTrue(parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_18.description(), TITLE_ID));
	}
	
	@Test(expected = TitleNotFoundException.class)
	public void whenSendsWrongMovieIDShouldThrowEsception() throws TitleNotFoundException, TechnicalFailureException {
		parentalControlService = new ParentalControlServiceImpl(movieServiceMock);
		
		when(movieServiceMock.getParentalControlLevel("Does not exists")).thenThrow(TitleNotFoundException.class);
		parentalControlService.canWatchMovie(ParentalControlLevel.LEVEL_18.description(), "Does not exists");
	}
	
	@Test(expected = TechnicalFailureException.class)
	public void whenSendsWrongLevelShouldThrowEsception() throws TitleNotFoundException, TechnicalFailureException {
		parentalControlService = new ParentalControlServiceImpl(movieServiceMock);
		
		when(movieServiceMock.getParentalControlLevel(TITLE_ID)).thenReturn("15");
		parentalControlService.canWatchMovie("Level unknown", TITLE_ID);
	}

}
