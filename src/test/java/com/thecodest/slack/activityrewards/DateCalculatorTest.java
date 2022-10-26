package com.thecodest.slack.activityrewards;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DateCalculatorTest {

	private DateCalculator sut;

	@BeforeEach
	void setUp() {
		sut = new DateCalculator();
	}

	@Test
	void shouldReturn1stOfPreviousMonth() {
		var result = sut.getPreviousMonthStart(LocalDateTime.of(2022, Month.OCTOBER, 22, 13, 37));
		Assertions.assertThat(result)
				.hasMonth(Month.SEPTEMBER)
				.hasDayOfMonth(1)
				.hasYear(2022)
				.hasHour(0)
				.hasMinute(0)
				.hasSecond(0);
	}

	@Test
	void shouldReturn1stOfPreviousMonthInPreviousYear() {
		var result = sut.getPreviousMonthStart(LocalDateTime.of(2022, Month.JANUARY, 22, 13, 37));
		Assertions.assertThat(result)
				.hasMonth(Month.DECEMBER)
				.hasDayOfMonth(1)
				.hasYear(2021)
				.hasHour(0)
				.hasMinute(0)
				.hasSecond(0);
	}

	@Test
	void shouldReturn1stOfThisMonth() {
		var result = sut.getThisMonthStart(LocalDateTime.of(2022, Month.OCTOBER, 22, 13, 37));
		Assertions.assertThat(result)
				.hasMonth(Month.OCTOBER)
				.hasDayOfMonth(1)
				.hasYear(2022)
				.hasHour(0)
				.hasMinute(0)
				.hasSecond(0);
	}
}