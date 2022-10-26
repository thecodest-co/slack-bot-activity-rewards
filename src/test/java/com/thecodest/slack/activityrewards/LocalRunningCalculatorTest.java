package com.thecodest.slack.activityrewards;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import static java.time.Month.APRIL;
import static java.time.Month.AUGUST;
import static java.time.Month.DECEMBER;
import static java.time.Month.FEBRUARY;
import static java.time.Month.JANUARY;
import static java.time.Month.JULY;
import static java.time.Month.JUNE;
import static java.time.Month.MARCH;
import static java.time.Month.MAY;
import static java.time.Month.NOVEMBER;
import static java.time.Month.OCTOBER;
import static java.time.Month.SEPTEMBER;
import static java.util.stream.Stream.concat;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LocalRunningCalculatorTest {

	private final LocalRunningCalculator sut = new LocalRunningCalculator();

	static Stream<Arguments> validDates() {
		return Stream.of(
				Arguments.of(1, MONDAY),
				Arguments.of(1, TUESDAY),
				Arguments.of(1, WEDNESDAY),
				Arguments.of(1, THURSDAY),
				Arguments.of(1, FRIDAY),
				Arguments.of(2, MONDAY),
				Arguments.of(3, MONDAY),
				Arguments.of(1, MONDAY),
				Arguments.of(1, TUESDAY),
				Arguments.of(1, WEDNESDAY),
				Arguments.of(1, THURSDAY),
				Arguments.of(1, FRIDAY));
	}

	static Stream<Arguments> invalidDates() {
		var weekend = Stream.of(
				Arguments.of(1, SATURDAY),
				Arguments.of(1, SUNDAY),
				Arguments.of(2, SUNDAY));

		var notFirstDayOfMonth = Stream.of(
				Arguments.of(4, MONDAY),
				Arguments.of(5, MONDAY),
				Arguments.of(6, WEDNESDAY),
				Arguments.of(7, THURSDAY),
				Arguments.of(10, FRIDAY),
				Arguments.of(22, WEDNESDAY),
				Arguments.of(11, THURSDAY),
				Arguments.of(10, FRIDAY));

		var notFirstWorkingDay = Stream.of(
				Arguments.of(2, TUESDAY),
				Arguments.of(3, TUESDAY),
				Arguments.of(2, WEDNESDAY),
				Arguments.of(3, WEDNESDAY),
				Arguments.of(2, THURSDAY),
				Arguments.of(3, THURSDAY),
				Arguments.of(2, FRIDAY),
				Arguments.of(3, FRIDAY),
				Arguments.of(2, TUESDAY),
				Arguments.of(3, TUESDAY));

		return concat(
				concat(weekend, notFirstDayOfMonth), notFirstWorkingDay);
	}

	@ParameterizedTest()
	@MethodSource("validDates")
	void shouldReturnTrueForValidDays(int day, DayOfWeek dayOfWeek) {
		assertThat(sut.shouldItRun(day, dayOfWeek)).isTrue();
	}

	@ParameterizedTest()
	@MethodSource("invalidDates")
	void shouldReturnFalseForInvalidDays(int day, DayOfWeek dayOfWeek) {
		assertThat(sut.shouldItRun(day, dayOfWeek)).isFalse();
	}
}