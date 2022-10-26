package com.thecodest.slack.activityrewards.slack;

import static java.util.function.Predicate.not;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.conversations.ConversationsHistoryRequest;
import com.slack.api.methods.request.users.UsersListRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.users.UsersListResponse;
import com.thecodest.slack.activityrewards.MessageBeing;
import io.vavr.control.Try;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.extern.java.Log;

@Log
class SimpleSlackClient implements SlackClient {

	private final MethodsClient methods;

	@Inject
	SimpleSlackClient(@Named("slack-token") String token) {
		this.methods = Slack.getInstance().methods(token);
	}

	@Override
	public ChatPostMessageResponse sendMessageToUser(SlackUser slackUser, String message) {
		log.info("Send message to " + slackUser.normalizedEmail());
		return Try.of(() -> methods.chatPostMessage(req -> req.channel(slackUser.slackId()).text(message)))
				.onFailure(Throwable::printStackTrace)
				.get();
	}

	@Override
	public ChatPostMessageResponse sendMessageToChannel(String slackChannel, String message) {
		log.info("Send message to " + slackChannel);
		return Try.of(() -> methods.chatPostMessage(req -> req.channel(slackChannel).text(message)))
				.onFailure(Throwable::printStackTrace)
				.get();
	}

	@Override
	public List<SlackUser> getUsersByEmails(List<String> emails) {
		log.info("Get users from slack");
		log.info(emails.toString());
		return getUsersByFilter(su -> emails.contains(su.normalizedEmail()));
	}

	@Override
	public List<SlackUser> getUsersByIds(List<String> ids) {
		log.info("Get users from slack");
		log.info(ids.toString());
		final Predicate<SlackUser> idsPredicate = su -> ids.contains(su.slackId());
		final Predicate<SlackUser> cody = su -> su.name().equalsIgnoreCase("cody");
		return getUsersByFilter(idsPredicate.and(not(cody)));
	}

	@Override
	public List<MessageBeing> getMessagesFromChannel(String channelId, LocalDateTime from, LocalDateTime to) {
		ConversationsHistoryRequest request = ConversationsHistoryRequest.builder()
				.channel(channelId)
				.oldest(toTs(from))
				.latest(toTs(to))
				.build();
		return Try.of(() -> methods.conversationsHistory(request))
				.map(chr -> chr.getMessages()
						.stream()
						.map(m -> new MessageBeing(m.getUser(), m.hashCode()))
						.toList())
				.getOrElse(Collections.emptyList());
	}

	private String toTs(LocalDateTime ldt) {
		if (ldt == null) {
			return null;
		}
		return ldt.atZone(ZoneId.systemDefault()).toEpochSecond() + "";
	}

	private List<SlackUser> getUsersByFilter(Predicate<SlackUser> predicate) {
		return Try.of(() -> methods
				.usersList(UsersListRequest.builder().build()))
				.onFailure(Throwable::printStackTrace)
				.map(UsersListResponse::getMembers)
				.getOrElse(Collections.emptyList())
				.stream()
				.filter(u -> u.getProfile().getEmail() != null)
				.map(u -> new SlackUser(u.getId(), u.getName(), u.getProfile().getEmail()))
				.filter(predicate)
				.toList();
	}
}
