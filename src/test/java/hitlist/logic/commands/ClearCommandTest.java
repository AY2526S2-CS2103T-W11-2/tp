package hitlist.logic.commands;

import static hitlist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static hitlist.testutil.TypicalPersons.getTypicalHitList;

import org.junit.jupiter.api.Test;

import hitlist.model.HitList;
import hitlist.model.Model;
import hitlist.model.ModelManager;
import hitlist.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalHitList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalHitList(), new UserPrefs());
        expectedModel.setHitList(new HitList());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
