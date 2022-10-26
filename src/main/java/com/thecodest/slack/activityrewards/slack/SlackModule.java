package com.thecodest.slack.activityrewards.slack;

import com.google.inject.AbstractModule;

/**
 * Configuration of Slack client.
 */
public class SlackModule extends AbstractModule {

	/**
	 * Just configure @{@link SlackClient} interface.
	 */
	@Override
	protected void configure() {
		bind(SlackClient.class).to(SimpleSlackClient.class);
	}
}
