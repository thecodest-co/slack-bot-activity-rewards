package com.thecodest.slack.activityrewards;

import java.text.ParseException;
import java.util.Objects;

/**
 * PoC of email normalization tool.
 *
 * <p>Normalization mean:
 *
 * <ul>
 * <li>trim all whitespaces and other blanks around address</li>
 * <li>domain part (after @) to lowercase</li>
 * <li>local part cleansed from aliases – everything between + and @</li>
 * <li>local part to lowercase – this is not valid against RFC but most of
 * software tread local part as case insensitive</li>
 * </ul>
 *
 * <p>Product of normalization is @NormalizedEmail class.
 */
public class EmailNormalization {

	/**
	 * Normalize given email address.
	 *
	 * @param email
	 *            raw address.
	 * @return Normalized address.
	 * @throws ParseException
	 *             when #email is not correct email address.
	 */
	public static NormalizedEmail normalize(String email) throws ParseException {
		Objects.requireNonNull(email);
		final int domainIndex = email.lastIndexOf('@');
		if (domainIndex == -1 || domainIndex == 0 || domainIndex + 1 == email.length()) {
			throw new ParseException("This is not an email!", 0);
		}

		final String localPartMaybeWithAliases = email.substring(0, domainIndex).trim();
		final String domain = email.substring(domainIndex + 1).trim().toLowerCase();

		final int aliasIndex = localPartMaybeWithAliases.indexOf('+');
		if (aliasIndex > 0) {
			return processAlias(localPartMaybeWithAliases, aliasIndex, domain, email);
		}

		final String normalizedLocalPart = localPartMaybeWithAliases.toLowerCase();

		return new NormalizedEmail(normalizedLocalPart + "@" + domain, email.trim(),
				normalizedLocalPart, domain, localPartMaybeWithAliases);
	}

	private static NormalizedEmail processAlias(String localPartMaybeWithAliases,
			int aliasIndex, String domain, String email) {
		final String localPart = localPartMaybeWithAliases.substring(0, aliasIndex);
		final String normalizedLocalPart = localPart.toLowerCase();
		return new NormalizedEmail(normalizedLocalPart + "@" + domain, email.trim(),
				normalizedLocalPart, domain, localPartMaybeWithAliases);
	}

	/**
	 * Email after normalization. Two emails are equal in meaning of
	 * {@link #equals(Object)} method,
	 * when they normal forms are equal.
	 *
	 * @param normalForm
	 *            normal form of address without aliases.
	 * @param commonForm
	 *            raw form.
	 * @param localPart
	 *            local part of email address (before @ symbol) without aliases.
	 * @param domainPart
	 *            domain part of email (after @ symbol).
	 * @param localPartWithAliases
	 *            local part of email address (before @ symbol) with all aliases.
	 */
	public record NormalizedEmail(String normalForm, String commonForm,
			String localPart, String domainPart,
			String localPartWithAliases) {

		/**
		 * Check equality in meaing of {@link #equals(Object)} method for common form.
		 *
		 * @param that
		 *            nullable {@link NormalizedEmail} object.
		 * @return true when {@link #commonForm}s are equal. Otherwise, return false.
		 */
		public boolean strictEquals(NormalizedEmail that) {
			return Objects.equals(this.commonForm, that.commonForm);
		}

		/**
		 * Check equality of objects based on {@link #normalForm}.
		 *
		 * @param o
		 *            another object.
		 * @return true when {@link #normalForm}s are equal. Otherwise, return false.
		 */
		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			NormalizedEmail that = (NormalizedEmail) o;
			return normalForm.equals(that.normalForm);
		}

		/**
		 * Calculates hashCode based on {@link #normalForm}.
		 *
		 * @return hashCode value of this object.
		 */
		@Override
		public int hashCode() {
			return Objects.hash(normalForm);
		}
	}

}
