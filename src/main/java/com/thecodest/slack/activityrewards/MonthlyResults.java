package com.thecodest.slack.activityrewards;

import java.time.Month;
import java.util.List;

public record MonthlyResults(Month month, int year, List<Result> results) {
}
