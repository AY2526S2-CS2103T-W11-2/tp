package hitlist.model.group;

import static hitlist.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GroupNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GroupName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidGroupName = "";
        assertThrows(IllegalArgumentException.class, () -> new GroupName(invalidGroupName));
    }

    @Test
    public void isValidGroupName() {
        // null name
        assertThrows(NullPointerException.class, () -> GroupName.isValidGroupName(null));

        // invalid name
        assertFalse(GroupName.isValidGroupName("")); // empty string
        assertFalse(GroupName.isValidGroupName(" ")); // spaces only
        assertFalse(GroupName.isValidGroupName("^")); // only non-alphanumeric characters
        assertFalse(GroupName.isValidGroupName("NUS AY25/26")); // contains non-alphanumeric characters

        // valid name
        assertTrue(GroupName.isValidGroupName("students")); // alphabets only
        assertTrue(GroupName.isValidGroupName("12345")); // numbers only
        assertTrue(GroupName.isValidGroupName("NUS AY2526")); // alphanumeric characters
        assertTrue(GroupName.isValidGroupName("Unemployed")); // with capital letters
        assertTrue(GroupName.isValidGroupName("A Group Dedicated to the People Who Spent Four Years"
                                              + "Learning How to Perfectly Format a Bibliography"
                                              + "While Simultaneously Forgetting How to Maintain"
                                              + "a Healthy Sleep Schedule")); // long names
    }

    @Test
    public void equals() {
        GroupName name = new GroupName("Valid Group Name");

        // same values -> returns true
        assertTrue(name.equals(new GroupName("Valid Group Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // different case -> returns true
        assertTrue(name.equals(new GroupName("Valid group name")));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new GroupName("Other Valid Group Name")));
    }
}
