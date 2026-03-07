package hitlist.logic.commands;

import static hitlist.logic.commands.CommandTestUtil.assertCommandFailure;
import static hitlist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static hitlist.testutil.TypicalPersons.getTypicalHitList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hitlist.logic.Messages;
import hitlist.model.Model;
import hitlist.model.ModelManager;
import hitlist.model.UserPrefs;
import hitlist.model.person.Person;
import hitlist.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalHitList(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getHitList(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getHitList().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model,
                AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
