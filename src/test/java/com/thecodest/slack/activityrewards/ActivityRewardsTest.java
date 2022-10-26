package com.thecodest.slack.activityrewards;

import static java.util.List.of;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.thecodest.slack.activityrewards.slack.SlackClient;
import com.thecodest.slack.activityrewards.slack.SlackUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ActivityRewardsTest {

	@Spy
	private LocalRunningCalculator localRunningCalculator;

	@Mock
	private SlackClient slackClient;

	@Mock
	private Configuration configuration;

	@InjectMocks
	private ActivityRewards activityRewards;

}