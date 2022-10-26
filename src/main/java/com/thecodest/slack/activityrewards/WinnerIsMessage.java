package com.thecodest.slack.activityrewards;

public class WinnerIsMessage {

	private final String message;
	// CHECKSTYLE:OFF

	public WinnerIsMessage() {
		this.message = """
				It's  time to announce this month's The Codest Slack Star - the most active Slack user of the month. Let's see who is the winner this time 👇🏼

				<drums,please>

				🥁🥁🥁🥁🥁🥁🥁🥁

				Officially this month's The Codest Slack Star is

				                                       <@%s>!

				%s Congratulations your keyboard was on fire this month 🔥 As a real Slackoholic you just won a 25$ worth amazon card. Contact Iza to receive the award and spend it as you wish 🚀

				""";
	}
	// CHECKSTYLE:ON

	public String getMessage(Result result) {
		return String.format(message, result.slackUser().slackId(), result.slackUser().name());
	}

}
