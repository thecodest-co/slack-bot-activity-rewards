FROM openjdk:17-jdk-alpine3.14

ENV SLACK_TOKEN ''
ENV CALAMARI_USERNAME ''
ENV CALAMARI_PASSWORD ''
ENV DAYS_LIMIT ''

COPY target/holiday-reminder-0.0.1.jar /holiday-reminder.jar

ENTRYPOINT ["java", "-jar", "/holiday-reminder.jar"]