package hitlist.logic.commands;

import static hitlist.testutil.Assert.assertThrows;
import static hitlist.testutil.TypicalCompanies.DELL;
import static hitlist.testutil.TypicalCompanies.GOOGLE;
import static hitlist.testutil.TypicalCompanies.META;
import static hitlist.testutil.TypicalCompanies.MICROSOFT;
import static hitlist.testutil.TypicalCompanies.getTypicalHitList;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hitlist.logic.Messages;
import hitlist.logic.commands.exceptions.CommandException;
import hitlist.model.HitList;
import hitlist.model.Model;
import hitlist.model.ModelManager;
import hitlist.model.ModelStub;
import hitlist.model.ReadOnlyHitList;
import hitlist.model.UserPrefs;
import hitlist.model.company.Company;
import hitlist.model.company.CompanyName;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCompanyCommand}.
 */
public class DeleteCompanyCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalHitList(), new UserPrefs());
    }

    @AfterEach
    public void tearDown() {
        model = null;
    }

    @Test
    public void constructor_nullCompanyName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteCompanyCommand(null));
    }

    @Test
    public void execute_validCompanyName_success() throws Exception {
        Company companyToDelete = GOOGLE;
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(companyToDelete.getName());

        String expectedMessage = String.format(DeleteCompanyCommand.MESSAGE_DELETE_COMPANY_SUCCESS,
                Messages.formatCompany(companyToDelete));

        ModelManager expectedModel = new ModelManager(model.getHitList(), new UserPrefs());
        expectedModel.deleteCompany(companyToDelete);
        assertCommandSuccess(deleteCompanyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidCompanyName_throwsCommandException() {
        CompanyName invalidCompanyName = new CompanyName("NonExistentCompany");
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(invalidCompanyName);

        String expectedMessage = String.format(DeleteCompanyCommand.MESSAGE_COMPANY_NOT_FOUND, invalidCompanyName);
        assertCommandFailure(deleteCompanyCommand, model, expectedMessage);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(GOOGLE.getName());
        assertThrows(NullPointerException.class, () -> deleteCompanyCommand.execute(null));
    }

    @Test
    public void execute_deleteMicrosoftCompany_success() throws Exception {
        Company companyToDelete = MICROSOFT;
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(companyToDelete.getName());

        String expectedMessage = String.format(DeleteCompanyCommand.MESSAGE_DELETE_COMPANY_SUCCESS,
                Messages.formatCompany(companyToDelete));

        ModelManager expectedModel = new ModelManager(model.getHitList(), new UserPrefs());
        expectedModel.deleteCompany(companyToDelete);
        assertCommandSuccess(deleteCompanyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteDellCompany_success() throws Exception {
        Company companyToDelete = DELL;
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(companyToDelete.getName());

        String expectedMessage = String.format(DeleteCompanyCommand.MESSAGE_DELETE_COMPANY_SUCCESS,
                Messages.formatCompany(companyToDelete));

        ModelManager expectedModel = new ModelManager(model.getHitList(), new UserPrefs());
        expectedModel.deleteCompany(companyToDelete);

        assertCommandSuccess(deleteCompanyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteMetaCompany_success() throws Exception {
        Company companyToDelete = META;
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(companyToDelete.getName());

        String expectedMessage = String.format(DeleteCompanyCommand.MESSAGE_DELETE_COMPANY_SUCCESS,
                Messages.formatCompany(companyToDelete));

        ModelManager expectedModel = new ModelManager(model.getHitList(), new UserPrefs());
        expectedModel.deleteCompany(companyToDelete);
        assertCommandSuccess(deleteCompanyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_caseInsensitiveCompanyName_success() throws Exception {
        Company companyToDelete = GOOGLE;
        CompanyName lowercaseCompanyName = new CompanyName(companyToDelete.getName().toString().toLowerCase());
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(lowercaseCompanyName);

        String expectedMessage = String.format(DeleteCompanyCommand.MESSAGE_DELETE_COMPANY_SUCCESS,
                Messages.formatCompany(companyToDelete));

        ModelManager expectedModel = new ModelManager(model.getHitList(), new UserPrefs());
        expectedModel.deleteCompany(companyToDelete);

        assertCommandSuccess(deleteCompanyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_partialCompanyName_throwsCommandException() {
        CompanyName partialCompanyName = new CompanyName("Google");
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(partialCompanyName);

        String expectedMessage = String.format(DeleteCompanyCommand.MESSAGE_COMPANY_NOT_FOUND, partialCompanyName);
        assertCommandFailure(deleteCompanyCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        CompanyName companyNameGoogle = GOOGLE.getName();
        CompanyName companyNameMicrosoft = MICROSOFT.getName();

        DeleteCompanyCommand deleteGoogleCommand = new DeleteCompanyCommand(companyNameGoogle);
        DeleteCompanyCommand deleteMicrosoftCommand = new DeleteCompanyCommand(companyNameMicrosoft);

        assertTrue(deleteGoogleCommand.equals(deleteGoogleCommand));

        DeleteCompanyCommand deleteGoogleCommandCopy = new DeleteCompanyCommand(companyNameGoogle);
        assertTrue(deleteGoogleCommand.equals(deleteGoogleCommandCopy));

        assertFalse(deleteGoogleCommand.equals(1));

        assertFalse(deleteGoogleCommand.equals(null));

        assertFalse(deleteGoogleCommand.equals(deleteMicrosoftCommand));
    }

    @Test
    public void toStringMethod() {
        CompanyName companyName = GOOGLE.getName();
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(companyName);

        String expectedString = DeleteCompanyCommand.class.getCanonicalName()
                + "{companyName=" + companyName + "}";

        assertEquals(expectedString, deleteCompanyCommand.toString());
    }

    /**
     * Executes the given {@code command}, confirms that
     * - the returned {@link CommandResult} matches {@code expectedCommandResult}
     * - the {@code actualModel} matches {@code expectedModel}
     */
    private void assertCommandSuccess(DeleteCompanyCommand command, Model actualModel, String expectedMessage,
            Model expectedModel) throws CommandException {

        CommandResult result = command.execute(actualModel);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, actualModel);
    }

    /**
     * Executes the given {@code command}, confirms that a {@code CommandException}
     * is thrown and that the resulting error message matches {@code expectedMessage}.
     */
    private void assertCommandFailure(DeleteCompanyCommand command, Model model, String expectedMessage) {
        assertThrows(CommandException.class, expectedMessage, () -> command.execute(model));
    }

    /**
     * A Model stub that contains a single company.
     */
    private class ModelStubWithCompany extends ModelStub {

        private final Company company;

        ModelStubWithCompany(Company company) {
            requireNonNull(company);
            this.company = company;
        }

        @Override
        public Optional<Company> getCompany(CompanyName companyName) {
            requireNonNull(companyName);

            if (this.company.getName().toString().equalsIgnoreCase(companyName.toString())) {
                return Optional.of(this.company);
            }

            return Optional.empty();
        }

        @Override
        public void deleteCompany(Company company) {
            requireNonNull(company);
        }

        @Override
        public ReadOnlyHitList getHitList() {
            HitList hitList = new HitList();
            hitList.addCompany(company);
            return hitList;
        }
    }

    /**
     * A Model stub that does not contain any company.
     */
    private class ModelStubWithoutCompany extends ModelStub {

        @Override
        public Optional<Company> getCompany(CompanyName companyName) {
            requireNonNull(companyName);
            return Optional.empty();
        }

        @Override
        public ReadOnlyHitList getHitList() {
            return new HitList();
        }
    }
}
