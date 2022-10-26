package com.thecodest.slack.activityrewards;

import static io.github.pellse.assembler.stream.StreamAdapter.streamAdapter;

import com.google.common.annotations.VisibleForTesting;
import com.thecodest.slack.activityrewards.aws.CustomS3Persistence;
import com.thecodest.slack.activityrewards.slack.SlackClient;
import com.thecodest.slack.activityrewards.slack.SlackUser;
import io.github.pellse.assembler.AssemblerBuilder;
import io.github.pellse.util.query.MapperUtils;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor(onConstructor_ = {@Inject})
class ActivityRewards implements Runnable {

	private final SlackClient slackClient;
	private final RunningCalculator localRunningCalculator;

	private final DateCalculator dateCalculator;

	private final CustomS3Persistence s3Persistence;

	private final Configuration configuration;

	@Override
	public void run() {
		if (!localRunningCalculator.shouldItRun()) {
			log.info("Not today");
			return;
		}

		var previousMonthStart = dateCalculator.getPreviousMonthStart();
		var messagesFromChannel = slackClient.getMessagesFromChannel(configuration.slackChannel(),
				previousMonthStart,
				dateCalculator.getThisMonthStart());
		var monthlyResults = calculateScore(messagesFromChannel, previousMonthStart);
		// s3Persistence.save(monthlyResults);
		sendMessage(monthlyResults);
	}

	@VisibleForTesting
	void sendMessage(MonthlyResults monthlyResults) {
		var max = monthlyResults.results().stream().max(Comparator.comparing(Result::score)).get();
		WinnerIsMessage winnerIsMessage = new WinnerIsMessage();
		final String message = winnerIsMessage.getMessage(max);
		slackClient.sendMessageToChannel(configuration.slackChannel(), message);
	}

	@VisibleForTesting
	MonthlyResults calculateScore(List<MessageBeing> messageBeings, LocalDateTime monthStart) {
		final List<Tuple2<String, Long>> resultsRaw = messageBeings.stream()
				.collect(Collectors.groupingBy(MessageBeing::userId, Collectors.counting()))
				.entrySet()
				.stream()
				.map(e -> Tuple.of(e.getKey(), e.getValue()))
				.toList();
		var results = AssemblerBuilder.assemblerOf(Result.class)
				.withIdExtractor((Tuple2<String, Long> t) -> t._1)
				.withAssemblerRules(
						MapperUtils.oneToOne(slackClient::getUsersByIds, SlackUser::slackId),
						(t, su) -> new Result(su, t._2))
				.using(streamAdapter())
				.assemble(resultsRaw)
				.filter(r -> r.slackUser() != null)
				.toList();
		return new MonthlyResults(monthStart.getMonth(), monthStart.getYear(), results);
	}
}
