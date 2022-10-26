package com.thecodest.slack.activityrewards;

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
 */
public interface RunningCalculator {
	/**
	 * Check if current local date is valid.
	 *
	 * @return true if date match to the rules. Otherwise, return false.
	 */
	boolean shouldItRun();
}
