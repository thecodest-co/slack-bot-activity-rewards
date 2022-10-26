package com.thecodest.slack.activityrewards.slack;

import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.thecodest.slack.activityrewards.MessageBeing;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Simplified Slack interface. If you want to use some additional APIs then you
 * need to add that method here.
 * Idea was that to have a simple „only things that we need” interface.
 */
public interface SlackClient {

	/**
	 * Send message to user. Message text is send as it is. If you need format your
	 * message you need to send proper
	 * formatting tags here.
	 *
	 * @param slackUser
	 *            target user.
	 * @param message
	 *            message as text.
	 * @return server response.
	 */
	ChatPostMessageResponse sendMessageToUser(SlackUser slackUser, String message);

	/**
	 * Send message to channel (or user). Message text is send as it is. If you need
	 * format your
	 * message you need to send proper
	 * formatting tags here.
	 *
	 * @param slackChannel
	 *            target channel.
	 * @param message
	 *            message as text.
	 * @return server response.
	 */
	ChatPostMessageResponse sendMessageToChannel(String slackChannel, String message);

	/**
	 * Find all users that emails match to given list.
	 *
	 * @param emails
	 *            list of user emails that se looking for.
	 * @return List of @{@link SlackUser}s that we found.
	 */
	List<SlackUser> getUsersByEmails(List<String> emails);

	/**
	 * Find all users that IDs match to given list.
	 *
	 * @param ids
	 *            list of user ids that se looking for.
	 * @return List of @{@link SlackUser}s that we found.
	 */
	List<SlackUser> getUsersByIds(List<String> ids);

	/**
	 * Return all messages from channel with given ID
	 *
	 * @param channelId
	 *            id of slack channel
	 * @param from
	 * @param to
	 * @return
	 */
	List<MessageBeing> getMessagesFromChannel(String channelId, LocalDateTime from, LocalDateTime to);

}
