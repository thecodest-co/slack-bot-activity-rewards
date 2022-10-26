package com.thecodest.slack.activityrewards.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

class AWSCredentialsProvider implements Provider<AWSCredentials> {

	private final String awsSecretKey;
	private final String awsAccessKey;

	@Inject
	public AWSCredentialsProvider(
			@Named("aws-access-key") String awsAccessKey,
			@Named("aws-secret-key") String awsSecretKey) {
		this.awsAccessKey = awsAccessKey;
		this.awsSecretKey = awsSecretKey;
	}

	@Override
	public AWSCredentials get() {
		return new BasicAWSCredentials(
				awsAccessKey,
				awsSecretKey);
	}
}
