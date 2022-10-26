package com.thecodest.slack.activityrewards.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thecodest.slack.activityrewards.MonthlyResults;
import io.vavr.control.Try;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.inject.Inject;
import lombok.extern.java.Log;

@Log
class CustomS3PersistenceImpl implements CustomS3Persistence {

	private static final String BUCKET_NAME = "activity-reward";
	private final AmazonS3 amazonS3;
	private final ObjectMapper objectMapper;

	@Inject
	CustomS3PersistenceImpl(AmazonS3 amazonS3, ObjectMapper objectMapper) {
		this.amazonS3 = amazonS3;
		this.objectMapper = objectMapper;
	}

	@Override
	public void save(MonthlyResults results) {
		if (!amazonS3.doesBucketExistV2(BUCKET_NAME)) {
			amazonS3.createBucket(BUCKET_NAME);
		}
		var awsOperationResult = Try
				.of(() -> Paths.get("./", "target", results.year() + "-" + results.month().toString() + ".json"))
				.mapTry(path -> {
					if (!path.toFile().exists())
						return Files.createFile(path);
					return path;
				})
				.map(Path::toFile)
				.andThenTry(f -> objectMapper.writeValue(f, results))
				.map(file -> new PutObjectRequest(BUCKET_NAME, results.year() + "/" + results.month(), file))
				.map(putObjectRequest -> amazonS3.putObject(putObjectRequest))
				.get();
		log.info(awsOperationResult.getVersionId());
	}
}
