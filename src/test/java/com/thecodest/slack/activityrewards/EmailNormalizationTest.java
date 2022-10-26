package com.thecodest.slack.activityrewards;

import static com.thecodest.slack.activityrewards.EmailNormalization.normalize;
import static com.thecodest.slack.activityrewards.NormalizedEmailAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.text.ParseException;
import org.junit.jupiter.api.Test;

class EmailNormalizationTest {

	public static final String EXPECTED = "test@test.com";
	public static final String LOCAL = "test";
	public static final String DOMAIN = "test.com";

	@Test
	void shouldNormalizeCommonEmail() throws ParseException {
		assertThat(normalize(EXPECTED))
				.hasNormalForm(EXPECTED)
				.hasCommonForm(EXPECTED)
				.hasLocalPart(LOCAL)
				.hasDomainPart(DOMAIN)
				.hasLocalPartWithAliases(LOCAL);
		assertThat(normalize("test@test.com  "))
				.hasNormalForm(EXPECTED)
				.hasCommonForm(EXPECTED)
				.hasLocalPart(LOCAL)
				.hasDomainPart(DOMAIN)
				.hasLocalPartWithAliases(LOCAL);
		assertThat(normalize("   test@test.com"))
				.hasNormalForm(EXPECTED)
				.hasCommonForm(EXPECTED)
				.hasLocalPart(LOCAL)
				.hasDomainPart(DOMAIN)
				.hasLocalPartWithAliases(LOCAL);
		assertThat(normalize("   test@test.com    "))
				.hasNormalForm(EXPECTED)
				.hasCommonForm(EXPECTED)
				.hasLocalPart(LOCAL)
				.hasDomainPart(DOMAIN)
				.hasLocalPartWithAliases(LOCAL);
	}

	@Test
	void shouldNormalizeCasing() throws ParseException {
		assertThat(normalize("test@TEST.com"))
				.hasNormalForm(EXPECTED)
				.hasCommonForm("test@TEST.com")
				.hasLocalPart(LOCAL)
				.hasDomainPart(DOMAIN)
				.hasLocalPartWithAliases(LOCAL);
		assertThat(normalize("TEST@test.com"))
				.hasNormalForm(EXPECTED)
				.hasCommonForm("TEST@test.com")
				.hasLocalPart(LOCAL)
				.hasDomainPart(DOMAIN)
				.hasLocalPartWithAliases("TEST");
	}

	@Test
	void shouldNormalizeAliases() throws ParseException {
		assertThat(normalize("test+alias@test.com"))
				.hasNormalForm(EXPECTED)
				.hasCommonForm("test+alias@test.com")
				.hasLocalPart(LOCAL)
				.hasDomainPart(DOMAIN)
				.hasLocalPartWithAliases("test+alias");
	}

	@Test
	void shouldThrowNullPointer() {
		assertThatThrownBy(() -> normalize(null)).isInstanceOf(NullPointerException.class);
	}

	@Test
	void shouldThrowParseException() {
		assertThatThrownBy(() -> normalize("Some random text")).isInstanceOf(ParseException.class);
		assertThatThrownBy(() -> normalize("Some random text@")).isInstanceOf(ParseException.class);
		assertThatThrownBy(() -> normalize("@Some random text")).isInstanceOf(ParseException.class);
	}
}
