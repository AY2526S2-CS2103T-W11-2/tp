package hitlist.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import hitlist.commons.exceptions.IllegalValueException;
import hitlist.model.HitList;
import hitlist.model.ReadOnlyHitList;
import hitlist.model.person.Person;

/**
 * An Immutable HitList that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableHitList {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableHitList} with the given persons.
     */
    @JsonCreator
    public JsonSerializableHitList(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyHitList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableHitList}.
     */
    public JsonSerializableHitList(ReadOnlyHitList source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code HitList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public HitList toModelType() throws IllegalValueException {
        HitList addressBook = new HitList();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
        return addressBook;
    }

}
