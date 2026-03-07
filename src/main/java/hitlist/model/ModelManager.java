package hitlist.model;

import static hitlist.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import hitlist.commons.core.GuiSettings;
import hitlist.commons.core.LogsCenter;
import hitlist.model.person.Person;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final HitList hitList;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyHitList hitList, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(hitList, userPrefs);

        logger.fine("Initializing with address book: " + hitList + " and user prefs " + userPrefs);

        this.hitList = new HitList(hitList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.hitList.getPersonList());
    }

    public ModelManager() {
        this(new HitList(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getHitListFilePath() {
        return userPrefs.getHitListFilePath();
    }

    @Override
    public void setHitListFilePath(Path hitListFilePath) {
        requireNonNull(hitListFilePath);
        userPrefs.setHitListFilePath(hitListFilePath);
    }

    //=========== HitList ================================================================================

    @Override
    public void setHitList(ReadOnlyHitList hitList) {
        this.hitList.resetData(hitList);
    }

    @Override
    public ReadOnlyHitList getHitList() {
        return hitList;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return hitList.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        hitList.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        hitList.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        hitList.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return hitList.equals(otherModelManager.hitList)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
