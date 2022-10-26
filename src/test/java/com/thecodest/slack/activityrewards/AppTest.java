package com.thecodest.slack.activityrewards;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.amazonaws.regions.Regions;
import java.util.Map;
import org.junit.jupiter.api.Test;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;

class AppTest {

	@Test
	public void shouldAnswerWithTrue() {

		var props = Map.of(
				"SLACK_TOKEN", "SLACK-TOKEN",
				"SLACK_CHANNEL", "SLACK_CHANNEL",
				"AWS_ACCESS_KEY", "AWS_ACCESS_KEY",
				"AWS_SECRET_KEY", "AWS_SECRET_KEY",
				"AWS_REGION", Regions.EU_CENTRAL_1.getName());

		EnvironmentVariables env = new EnvironmentVariables(props);
		assertDoesNotThrow(() -> env.execute(() -> App.main(new String[]{})));
	}
}
