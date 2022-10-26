package com.thecodest.slack.activityrewards.aws;

import com.thecodest.slack.activityrewards.MonthlyResults;

public interface CustomS3Persistence {

	void save(MonthlyResults results);

}
