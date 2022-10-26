package com.thecodest.slack.activityrewards;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Utils for date calculation.
 *
 */
public class DateCalculator {

	public LocalDateTime getPreviousMonthStart() {
		return getPreviousMonthStart(LocalDateTime.now());
	}

	public LocalDateTime getPreviousMonthStart(LocalDateTime now) {
		return LocalDate.of(now.getYear(), now.getMonth(), 1).minusMonths(1).atStartOfDay();
	}

	public LocalDateTime getThisMonthStart() {
		return getThisMonthStart(LocalDateTime.now());
	}
	public LocalDateTime getThisMonthStart(LocalDateTime now) {
		return LocalDate.of(now.getYear(), now.getMonth(), 1).atStartOfDay();
	}

}
