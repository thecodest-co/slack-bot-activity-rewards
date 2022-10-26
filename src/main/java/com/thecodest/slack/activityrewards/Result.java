package com.thecodest.slack.activityrewards;

import com.thecodest.slack.activityrewards.slack.SlackUser;

public record Result(SlackUser slackUser, Long score) {
}
