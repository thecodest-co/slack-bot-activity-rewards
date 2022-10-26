package com.thecodest.slack.activityrewards;

import com.google.inject.Guice;
import com.thecodest.slack.activityrewards.auth.AuthViaEnvModule;
import com.thecodest.slack.activityrewards.aws.AwsModule;
import com.thecodest.slack.activityrewards.slack.SlackModule;

/**
 * Bootstrap class. Load and configure Guice Modules.
 */
public class App {

	/**
	 * No params.
	 *
	 * @param args
	 *            empty array
	 */
	public static void main(String[] args) {
		var injector = Guice.createInjector(
				new MasterModule(), new AuthViaEnvModule(),
				new SlackModule(), new AwsModule());
		var app = injector.getInstance(ActivityRewards.class);
		app.run();
	}

}
