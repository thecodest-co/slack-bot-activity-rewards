package com.thecodest.slack.activityrewards;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

class NormalizedEmailAssert extends AbstractAssert<NormalizedEmailAssert, EmailNormalization.NormalizedEmail> {

	protected NormalizedEmailAssert(EmailNormalization.NormalizedEmail emailNormalization) {
		super(emailNormalization, NormalizedEmailAssert.class);
	}

	public static NormalizedEmailAssert assertThat(EmailNormalization.NormalizedEmail emailNormalization) {
		return new NormalizedEmailAssert(emailNormalization);
	}

	public NormalizedEmailAssert hasNormalForm(String expected) {
		isNotNull();
		Assertions.assertThat(actual.normalForm())
				.isNotNull()
				.isEqualTo(expected);
		return this;
	}

	public NormalizedEmailAssert hasCommonForm(String expected) {
		isNotNull();
		Assertions.assertThat(actual.commonForm())
				.isNotNull()
				.isEqualTo(expected);
		return this;
	}

	public NormalizedEmailAssert hasLocalPart(String expected) {
		isNotNull();
		Assertions.assertThat(actual.localPart())
				.isNotNull()
				.isEqualTo(expected);
		return this;
	}

	public NormalizedEmailAssert hasDomainPart(String expected) {
		isNotNull();
		Assertions.assertThat(actual.domainPart())
				.isNotNull()
				.isEqualTo(expected);
		return this;
	}

	public NormalizedEmailAssert hasLocalPartWithAliases(String expected) {
		isNotNull();
		Assertions.assertThat(actual.localPartWithAliases())
				.isNotNull()
				.isEqualTo(expected);
		return this;
	}

	public NormalizedEmailAssert areStrictEquals(EmailNormalization.NormalizedEmail expected) {
		isNotNull();
		Assertions.assertThat(actual)
				.isNotNull()
				.matches(ne -> ne.strictEquals(expected), "NormalizedEmails are not strict equals");
		return this;
	}
}
