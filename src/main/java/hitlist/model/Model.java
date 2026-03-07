package hitlist.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import hitlist.commons.core.GuiSettings;
import hitlist.model.person.Person;
import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' hit list file path.
     */
    Path getHitListFilePath();

    /**
     * Sets the user prefs' hit list file path.
     */
    void setHitListFilePath(Path addressBookFilePath);

    /**
     * Replaces hit list data with the data in {@code hitList}.
     */
    void setHitList(ReadOnlyHitList hitList);

    /** Returns the HitList */
    ReadOnlyHitList getHitList();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the hit list.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the hit list.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the hit list.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the hit list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the hit list.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);
}
