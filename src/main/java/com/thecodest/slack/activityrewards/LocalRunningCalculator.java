package com.thecodest.slack.activityrewards;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

import com.google.common.annotations.VisibleForTesting;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * For requirement, run on first working day of each month.
 *
 * <p>This is very easy implementation that have only three rules:
 *
 * <ul>
 * <li>Run on 1st only if it is not saturday and sunday</li>
 * <li>Run on 2nd only if it is monday</li>
 * <li>Run on 3rd only if it is monday</li>
 * </ul>
 *
 */
class LocalRunningCalculator implements RunningCalculator {

	/**
	 * Check if current local date is valid.
	 *
	 * @return true if date match to the rules. Otherwise, return false.
	 */
	@Override
	public boolean shouldItRun() {
		LocalDate date = LocalDate.now();
		var day = date.getDayOfMonth();
		var dayOfWeek = date.getDayOfWeek();
		return shouldItRun(day, dayOfWeek);
	}

	@VisibleForTesting
	boolean shouldItRun(int day, DayOfWeek dayOfWeek) {
		return firstCheck(day, dayOfWeek) || mondayCheck(day, dayOfWeek);
	}

	private boolean firstCheck(int day, DayOfWeek dayOfWeek) {
		return day == 1 && dayOfWeek != SATURDAY && dayOfWeek != SUNDAY;
	}

	private boolean mondayCheck(int day, DayOfWeek dayOfWeek) {
		return dayOfWeek == MONDAY && (day == 2 || day == 3);
	}
}
