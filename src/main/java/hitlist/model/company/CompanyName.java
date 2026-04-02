package hitlist.model.company;

import static hitlist.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Company's name in the hit list.
 * Guarantees: immutable; is valid as declared in {@link #isValidCompanyName(String)}
 */
public class CompanyName {

    public static final String MESSAGE_CONSTRAINTS =
            "Company names must be between 2 and 30 characters long, "
                    + "cannot start with a space, and cannot contain forward slashes (/), or line breaks.";

    /**
     * The first character of the company name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * Forward slashes (/) are not allowed to prevent command parser conflicts.
     */
    public static final String VALIDATION_REGEX = "^[^\\s/][^/\\v]{1,29}$";

    private final String companyName;

    /**
     * Constructs a {@code CompanyName}.
     *
     * @param companyName A valid company name.
     */
    public CompanyName(String companyName) {
        requireNonNull(companyName);
        checkArgument(isValidCompanyName(companyName.trim()), MESSAGE_CONSTRAINTS);
        this.companyName = companyName.trim();
    }

    /**
     * Returns true if a given string is a valid company name.
     *
     * @param test The string to test for validity as a company name.
     * @return True if the string is a valid company name, false otherwise.
     */
    public static boolean isValidCompanyName(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return companyName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompanyName)) {
            return false;
        }

        CompanyName otherName = (CompanyName) other;
        return companyName.equalsIgnoreCase(otherName.companyName);
    }

    @Override
    public int hashCode() {
        return companyName.hashCode();
    }
}
