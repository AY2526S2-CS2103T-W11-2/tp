package hitlist.model;

import static hitlist.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static hitlist.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static hitlist.testutil.Assert.assertThrows;
import static hitlist.testutil.TypicalPersons.ALICE;
import static hitlist.testutil.TypicalPersons.getTypicalHitList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import hitlist.model.person.Person;
import hitlist.model.person.exceptions.DuplicatePersonException;
import hitlist.testutil.PersonBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HitListTest {

    private final HitList hitList = new HitList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), hitList.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> hitList.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        HitList newData = getTypicalHitList();
        hitList.resetData(newData);
        assertEquals(newData, hitList);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        HitListStub newData = new HitListStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> hitList.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> hitList.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(hitList.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        hitList.addPerson(ALICE);
        assertTrue(hitList.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        hitList.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(hitList.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> hitList.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = HitList.class.getCanonicalName() + "{persons=" + hitList.getPersonList() + "}";
        assertEquals(expected, hitList.toString());
    }

    /**
     * A stub ReadOnlyHitList whose persons list can violate interface constraints.
     */
    private static class HitListStub implements ReadOnlyHitList {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        HitListStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
