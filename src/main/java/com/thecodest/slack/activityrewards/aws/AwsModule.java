package com.thecodest.slack.activityrewards.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import java.util.Optional;

public class AwsModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(String.class)
				.annotatedWith(Names.named("aws-access-key"))
				.toInstance(getProperty("AWS_ACCESS_KEY", ""));
		bind(String.class)
				.annotatedWith(Names.named("aws-secret-key"))
				.toInstance(getProperty("AWS_SECRET_KEY", ""));
		bind(String.class)
				.annotatedWith(Names.named("aws-region"))
				.toInstance(getProperty("AWS_REGION", ""));
		bind(AWSCredentials.class).toProvider(AWSCredentialsProvider.class);
		bind(AmazonS3.class).toProvider(AmazonS3Provider.class);
		bind(ObjectMapper.class).toInstance(new ObjectMapper().registerModule(new JavaTimeModule()));
		bind(CustomS3Persistence.class).to(CustomS3PersistenceImpl.class);

	}

	private String getProperty(String propertyName, String defaultValue) {
		return Optional.ofNullable(System.getenv(propertyName)).orElse(defaultValue);
	}
}
