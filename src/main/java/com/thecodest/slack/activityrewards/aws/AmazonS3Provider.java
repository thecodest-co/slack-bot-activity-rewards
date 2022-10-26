package com.thecodest.slack.activityrewards.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

class AmazonS3Provider implements Provider<AmazonS3> {

	private final AWSCredentials credentials;
	private final String region;

	@Inject
	AmazonS3Provider(AWSCredentials credentials, @Named("aws-region") String region) {
		this.credentials = credentials;
		this.region = region;
	}

	@Override
	public AmazonS3 get() {
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.fromName(region))
				.build();
	}
}
