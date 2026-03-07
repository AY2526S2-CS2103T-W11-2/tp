package hitlist.testutil;

import hitlist.model.HitList;
import hitlist.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code HitList ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private HitList addressBook;

    public AddressBookBuilder() {
        addressBook = new HitList();
    }

    public AddressBookBuilder(HitList addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code HitList} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        addressBook.addPerson(person);
        return this;
    }

    public HitList build() {
        return addressBook;
    }
}
