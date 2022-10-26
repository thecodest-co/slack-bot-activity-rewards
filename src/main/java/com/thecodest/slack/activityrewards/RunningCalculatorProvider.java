package com.thecodest.slack.activityrewards;

import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.validation.constraints.NotNull;
import lombok.extern.java.Log;

@Log
class RunningCalculatorProvider implements Provider<RunningCalculator> {

	private final Boolean isTestRun;

	@Inject
	RunningCalculatorProvider(@NotNull @Named("test-run") Boolean isTestRun) {
		this.isTestRun = isTestRun;
	}

	@Override
	public RunningCalculator get() {
		return Optional.of(isTestRun)
				.stream()
				.peek(b -> log.warning("TEST_RUN is set to " + b))
				.map(b -> b ? new FakeRunningCalculator() : new LocalRunningCalculator())
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Ops!"));
	}
}
