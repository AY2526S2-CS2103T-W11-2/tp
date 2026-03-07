package hitlist.logic.commands;

import static hitlist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static hitlist.logic.commands.CommandTestUtil.showPersonAtIndex;
import static hitlist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static hitlist.testutil.TypicalPersons.getTypicalHitList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hitlist.model.Model;
import hitlist.model.ModelManager;
import hitlist.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalHitList(), new UserPrefs());
        expectedModel = new ModelManager(model.getHitList(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
