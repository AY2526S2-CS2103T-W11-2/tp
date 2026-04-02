package hitlist.model.group;

import static hitlist.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Group's name in the HitList.
 * Guarantees: immutable; is valid as declared in {@link #isValidGroupName(String)}
 */
public class GroupName {

    public static final String MESSAGE_CONSTRAINTS =
            "Group names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code GroupName}.
     *
     * @param name A valid group name.
     */
    public GroupName(String name) {
        requireNonNull(name);
        checkArgument(isValidGroupName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid group name.
     */
    public static boolean isValidGroupName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupName)) {
            return false;
        }

        GroupName otherGroupName = (GroupName) other;
        return fullName.toUpperCase().equals(otherGroupName.fullName.toUpperCase());
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
