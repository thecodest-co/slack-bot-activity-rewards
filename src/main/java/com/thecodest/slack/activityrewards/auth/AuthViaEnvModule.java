package com.thecodest.slack.activityrewards.auth;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Module configure authentication for Slack and Calamari.
 *
 * <p>Looking for:
 * <ul>
 * <li>SLACK_TOKEN</li>
 * <li>SLACK_CHANNEL</li>
 * </ul>
 * Environment variables.
 */
public class AuthViaEnvModule extends AbstractModule {

	/**
	 * Read environment variables and set them into named Strings.
	 * <ul>
	 * <li>SLACK_TOKEN » slack-token</li>
	 * <li>SLACK_CHANNEL » slack-channel</li>
	 * </ul>>
	 */
	@Override
	protected void configure() {
		bind(String.class)
				.annotatedWith(Names.named("slack-token"))
				.toInstance(System.getenv("SLACK_TOKEN"));
		bind(String.class)
				.annotatedWith(Names.named("slack-channel"))
				.toInstance(System.getenv("SLACK_CHANNEL"));

	}
}
