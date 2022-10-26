package com.thecodest.slack.activityrewards;

import javax.inject.Inject;
import javax.inject.Named;

record Configuration(
		@Named("slack-channel") String slackChannel) {

	@Inject
	Configuration(@Named("slack-channel") String slackChannel) {
		this.slackChannel = slackChannel;
	}
}
