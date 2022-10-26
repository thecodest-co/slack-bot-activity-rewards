package com.thecodest.slack.activityrewards;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.OptionalBinder;
import com.google.inject.name.Names;

class MasterModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Boolean.class)
				.annotatedWith(Names.named("test-run"))
				.toInstance(Boolean.valueOf(System.getenv("TEST_RUN")));
		bind(RunningCalculator.class).toProvider(RunningCalculatorProvider.class);
		bind(Configuration.class);
	}
}
