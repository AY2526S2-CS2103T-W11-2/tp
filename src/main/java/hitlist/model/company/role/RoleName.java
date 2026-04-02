package hitlist.model.company.role;

import static hitlist.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Role's name in the hit list.
 * Guarantees: immutable; is valid as declared in {@link #isValidRoleName(String)}
 */
public class RoleName {

    public static final String MESSAGE_CONSTRAINTS =
            "Role names must be between 2 and 50 characters long, "
                    + "cannot start with a space, and cannot contain forward slashes (/) or line breaks.";

    /**
     * The first character of the role name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * Forward slashes (/) are not allowed to prevent command parser conflicts.
     */
    public static final String VALIDATION_REGEX = "^[^\\s/][^/\\v]{1,49}$";

    private final String roleName;

    /**
     * Constructs a {@code RoleName}.
     *
     * @param roleName A valid role name.
     */
    public RoleName(String roleName) {
        requireNonNull(roleName);
        checkArgument(isValidRoleName(roleName.trim()), MESSAGE_CONSTRAINTS);
        this.roleName = roleName.trim();
        assert isValidRoleName(this.roleName) : "Role name must match validation rules";
    }

    /**
     * Returns true if a given string is a valid role name.
     *
     * @param test The string to test for validity as a role name.
     * @return True if the string is a valid role name, false otherwise.
     */
    public static boolean isValidRoleName(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return roleName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RoleName)) {
            return false;
        }

        RoleName otherName = (RoleName) other;
        return roleName.equalsIgnoreCase(otherName.roleName);
    }

    @Override
    public int hashCode() {
        return roleName.hashCode();
    }
}
