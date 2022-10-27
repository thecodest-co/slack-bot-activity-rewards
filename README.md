# Activity Rewards

[![Build & deploy to prod](https://github.com/thecodest-co/slack-bot-activity-rewards/actions/workflows/prod-deployment.yml/badge.svg)](https://github.com/thecodest-co/slack-bot-activity-rewards/actions/workflows/prod-deployment.yml)
[![Build update page](https://github.com/thecodest-co/slack-bot-activity-rewards/actions/workflows/page.yml/badge.svg)](https://github.com/thecodest-co/slack-bot-activity-rewards/actions/workflows/page.yml)

This is plugin to our slack-bot named Cody. On first working day of every month it sent information about winner of our
#random channel contest.

Technical page (maven reports etc.) [here](https://thecodest-co.github.io/slack-bot-activity-rewards/)

## Configuration

You need these environment properties set:

* `SLACK_TOKEN` – token for slack bot. This is `xoxb` token that you can get from Slack application management console.
* `SLACK_CHANNEL` – ID of slack channel that will be under contest.
* `AWS_ACCESS_KEY` – Access key to AWS.
* `AWS_SECRET_KEY` – Secret key to AWS.
* `AWS_REGION` – Region of AWS  S3 bucket to store results.
* `TEST_RUN` – optional parameter to indicate that you run application in dev/test mode. In this mode it ignores day
  running rules.

## Building

Just run:

```bash
mvn clean verify -P docker
```

this will build application and docker image in your local registry.

## Running

By default, this app works as AWS Lambda function and almost all deployment steps to AWS are handled by github actions.

### As jar

You need set up environment properties mentioned above and then:

```bash
java -jar target/activity-rewards.jar
```

### As Docker image

You need to pass environment properties mentioned above to container:

```bash
docker run --rm -e SLACK_TOKEN=<SLACK_TOKEN> \
  -e SLACK_CHANNEL=<SLACK_CHANNEL> \
  -e AWS_ACCESS_KEY=<AWS_ACCESS_KEY> \
  -e AWS_SECRET_KEY=<AWS_SECRET_KEY> \
  -e AWS_REGION=<AWS_REGION> \
  --name activity-rewards \
  -it activity-rewards:latest
```

remember about optional properties.

## AWS Configuration – requirements and deployment

Before you start you need to prepare your AWS services.

### Github

You need to set up some secrets:

- `AWS_ACCESS_KEY_ID`
- `AWS_SECRET_ACCESS_KEY`
- `AWS_REGION`
- `SLACK_TOKEN`
- `SLACK_CHANNEL`

First two are about your AWS account. Remember that if you use user „Access key - Programmatic access” then you need to
configure proper rights to AWS Lambda, AWS ECR and AWS Events. Other four are described above and will be copy to AWS
Lambda configuration.

### ECR Configuration

Go to AWS ECR and create new repository via console or using CLI:

```bash
aws ecr create-repository \
    --repository-name codest \
    --image-tag-mutability MUTABLE
```

Repository name should be the same as name in env var `ECR_REPOSITORY` in GH action file. Flag `MUTABLE` gives
possibility to redeploy image with the same TAG.

## Installation

Installation process is very easy. When you have configured all secrets and create ECR registry, just
run `prod-deployment` workflow. It will create docker Image, push it to registry and register new lambda function based
on this image.

### Post installation steps

After first installation you need to setup trigger that will be apply function. For this log in to AWS Lambda, and
select `activity-rewards` function. Then click `Add trigger` button:

![](/img/lambda1.png)

Select a source `EventBrige (CloudWatch Events)`:

![](/img/lambda2.png)

Then create new rule. Name it as you like and set `Schedule expression` to `rate(1 day)` and click add:

![](/img/lambda3.png)

That's all! You should see function with trigger

![](/img/lambda4.png)

## TODO

- [x] AWS integration
- [x] Configure GH workflows.
- [ ] Optional integration with abstractapi.com to extends our working day calculation.
- [ ] Add trigger configuration into actions
- [ ] Add central logger configuration
- [ ] Improve test coverage 