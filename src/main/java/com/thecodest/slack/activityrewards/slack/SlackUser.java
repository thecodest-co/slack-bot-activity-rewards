package com.thecodest.slack.activityrewards.slack;

import com.thecodest.slack.activityrewards.EmailNormalization;
import io.vavr.control.Try;

/**
 * This record represents The Codest employee on Slack.
 *
 * @param slackId
 *            internal Slack id.
 * @param name
 *            full name as it is set on Slack.
 * @param email
 *            email as it is set on Slack.
 */
public record SlackUser(String slackId, String name, String email) {

	/**
	 * Return user email in normalized form. See @{@link EmailNormalization}.
	 *
	 * @return normalized email
	 */
	public String normalizedEmail() {
		return Try.success(email())
				.mapTry(EmailNormalization::normalize)
				.map(EmailNormalization.NormalizedEmail::normalForm)
				.getOrElseThrow(t -> new RuntimeException(t));
	}
}
