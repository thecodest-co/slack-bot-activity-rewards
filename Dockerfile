FROM openjdk:17-jdk-alpine3.14

ENV SLACK_TOKEN ''
ENV SLACK_CHANNEL ''
ENV AWS_ACCESS_KEY ''
ENV AWS_SECRET_KEY ''
ENV AWS_REGION ''
ENV TEST_RUN ''

COPY target/activity-rewards-0.0.1.jar /activity-rewards.jar

ENTRYPOINT ["java", "-jar", "activity-rewards.jar"]