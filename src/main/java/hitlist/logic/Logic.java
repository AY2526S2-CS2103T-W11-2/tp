package hitlist.logic;

import java.nio.file.Path;

import hitlist.commons.core.GuiSettings;
import hitlist.logic.commands.CommandResult;
import hitlist.logic.commands.exceptions.CommandException;
import hitlist.logic.parser.exceptions.ParseException;
import hitlist.model.Model;
import hitlist.model.ReadOnlyHitList;
import hitlist.model.person.Person;
import javafx.collections.ObservableList;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the HitList.
     *
     * @see Model#getHitList()
     */
    ReadOnlyHitList getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
