package com.thecodest.slack.activityrewards;

/**
 * For test purposes only. Always return true.
 */
class FakeRunningCalculator implements RunningCalculator {

	/**
	 * Always return true.
	 *
	 * @return true and only true
	 */
	@Override
	public boolean shouldItRun() {
		return true;
	}
}
