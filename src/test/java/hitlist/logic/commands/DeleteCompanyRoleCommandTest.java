package hitlist.logic.commands;

import static hitlist.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hitlist.commons.core.index.Index;
import hitlist.logic.commands.exceptions.CommandException;
import hitlist.model.Model;
import hitlist.model.ModelManager;
import hitlist.model.UserPrefs;
import hitlist.model.company.Company;
import hitlist.model.company.CompanyName;
import hitlist.model.company.role.Role;
import hitlist.model.company.role.RoleName;
import hitlist.testutil.CompanyBuilder;
import hitlist.testutil.TypicalCompanies;
import hitlist.testutil.TypicalRoles;

public class DeleteCompanyRoleCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalCompanies.getTypicalHitList(), new UserPrefs());
    }

    @Test
    public void constructor_nullCompanyName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteCompanyRoleCommand((RoleName) null, null));
        assertThrows(NullPointerException.class, () -> new DeleteCompanyRoleCommand((Index) null, null));
    }

    @Test
    public void execute_validRoleName_success() throws Exception {
        String uniqueName = "DelRoleTest_" + System.currentTimeMillis();

        if (uniqueName.length() > 30) {
            uniqueName = uniqueName.substring(uniqueName.length() - 30);
        }

        Company companyToModify = new CompanyBuilder()
                .withName(uniqueName)
                .withDescription("Test company for delete role")
                .build();

        model.addCompany(companyToModify);

        Company modelCompany = model.getCompany(companyToModify.getName()).get();

        Role testRole1 = TypicalRoles.SOFTWARE_ENGINEER;
        Role testRole2 = TypicalRoles.PRODUCT_MANAGER;

        modelCompany.getUniqueRoleList().add(testRole1);
        modelCompany.getUniqueRoleList().add(testRole2);

        DeleteCompanyRoleCommand deleteRoleCommand = new DeleteCompanyRoleCommand(
                testRole1.getRoleName(),
                modelCompany.getName());

        String expectedMessage = String.format(DeleteCompanyRoleCommand.MESSAGE_SUCCESS,
                testRole1.getRoleName(), modelCompany.getName());

        CommandResult result = deleteRoleCommand.execute(model);

        assertEquals(expectedMessage, result.getFeedbackToUser());

        Company actualCompany = model.getCompany(modelCompany.getName()).get();

        assertFalse(actualCompany.getUniqueRoleList().contains(testRole1));
        assertTrue(actualCompany.getUniqueRoleList().contains(testRole2));
    }

    @Test
    public void execute_caseInsensitiveCompanyName_success() throws Exception {
        String uniqueName = "CaseRoleTest_" + System.currentTimeMillis();

        if (uniqueName.length() > 30) {
            uniqueName = uniqueName.substring(uniqueName.length() - 30);
        }

        Company companyToModify = new CompanyBuilder()
                .withName(uniqueName)
                .withDescription("Test company for case-insensitive delete role")
                .build();

        model.addCompany(companyToModify);

        Company modelCompany = model.getCompany(companyToModify.getName()).get();
        Role testRole = TypicalRoles.SOFTWARE_ENGINEER;
        Role remainingRole = TypicalRoles.PRODUCT_MANAGER;

        modelCompany.getUniqueRoleList().add(testRole);
        modelCompany.getUniqueRoleList().add(remainingRole);

        CompanyName lowercaseCompanyName = new CompanyName(modelCompany.getName().toString().toLowerCase());
        DeleteCompanyRoleCommand deleteRoleCommand =
                new DeleteCompanyRoleCommand(testRole.getRoleName(), lowercaseCompanyName);

        String expectedMessage = String.format(DeleteCompanyRoleCommand.MESSAGE_SUCCESS,
                testRole.getRoleName(), modelCompany.getName());

        CommandResult result = deleteRoleCommand.execute(model);

        assertEquals(expectedMessage, result.getFeedbackToUser());

        Company actualCompany = model.getCompany(modelCompany.getName()).get();
        assertFalse(actualCompany.getUniqueRoleList().contains(testRole));
        assertTrue(actualCompany.getUniqueRoleList().contains(remainingRole));
    }

    @Test
    public void execute_validRoleIndex_success() throws Exception {
        String uniqueName = "DelRoleIdxTest_" + System.currentTimeMillis();

        if (uniqueName.length() > 30) {
            uniqueName = uniqueName.substring(uniqueName.length() - 30);
        }

        Company companyToModify = new CompanyBuilder()
                .withName(uniqueName)
                .withDescription("Test company for delete role by index")
                .build();

        model.addCompany(companyToModify);
        Company modelCompany = model.getCompany(companyToModify.getName()).get();

        Role testRole1 = TypicalRoles.SOFTWARE_ENGINEER;
        Role testRole2 = TypicalRoles.PRODUCT_MANAGER;

        modelCompany.getUniqueRoleList().add(testRole1);
        modelCompany.getUniqueRoleList().add(testRole2);

        DeleteCompanyRoleCommand deleteRoleCommand = new DeleteCompanyRoleCommand(Index.fromOneBased(1),
                modelCompany.getName());

        String expectedMessage = String.format(DeleteCompanyRoleCommand.MESSAGE_SUCCESS,
                testRole1.getRoleName(), modelCompany.getName());

        CommandResult result = deleteRoleCommand.execute(model);

        assertEquals(expectedMessage, result.getFeedbackToUser());

        Company actualCompany = model.getCompany(modelCompany.getName()).get();

        assertFalse(actualCompany.getUniqueRoleList().contains(testRole1));
        assertTrue(actualCompany.getUniqueRoleList().contains(testRole2));
    }

    @Test
    public void execute_emptyRoleList_throwsCommandException() {
        String uniqueName = "EmptyRole_" + System.currentTimeMillis();

        if (uniqueName.length() > 30) {
            uniqueName = uniqueName.substring(uniqueName.length() - 30);
        }

        Company company = new CompanyBuilder()
                .withName(uniqueName)
                .withDescription("Company with no roles")
                .build();

        model.addCompany(company);
        DeleteCompanyRoleCommand deleteRoleCommand = new DeleteCompanyRoleCommand(Index.fromOneBased(1),
                company.getName());

        String expectedMessage = String.format(DeleteCompanyRoleCommand.MESSAGE_ROLE_INDEX_OUT_OF_BOUNDS,
                1, company.getName(), 0);

        assertThrows(CommandException.class, expectedMessage, () ->
                deleteRoleCommand.execute(model));
    }

    @Test
    public void equals() {
        RoleName roleName1 = new RoleName("Software Engineer");
        RoleName roleName2 = new RoleName("Product Manager");
        CompanyName companyName1 = new CompanyName("Google");
        CompanyName companyName2 = new CompanyName("Microsoft");

        Index index1 = Index.fromOneBased(1);
        Index index2 = Index.fromOneBased(2);

        DeleteCompanyRoleCommand deleteByName1 = new DeleteCompanyRoleCommand(roleName1, companyName1);
        DeleteCompanyRoleCommand deleteByName1Copy = new DeleteCompanyRoleCommand(roleName1, companyName1);
        DeleteCompanyRoleCommand deleteByName2 = new DeleteCompanyRoleCommand(roleName2, companyName1);
        DeleteCompanyRoleCommand deleteByIndex1 = new DeleteCompanyRoleCommand(index1, companyName1);
        DeleteCompanyRoleCommand deleteByIndex2 = new DeleteCompanyRoleCommand(index2, companyName1);
        DeleteCompanyRoleCommand deleteByIndex1DifferentCompany = new DeleteCompanyRoleCommand(index1, companyName2);

        assertTrue(deleteByName1.equals(deleteByName1));
        assertTrue(deleteByIndex1.equals(deleteByIndex1));
        assertTrue(deleteByName1.equals(deleteByName1Copy));
        assertFalse(deleteByName1.equals(1));
        assertFalse(deleteByName1.equals(null));

        assertFalse(deleteByName1.equals(deleteByName2));
        assertFalse(deleteByIndex1.equals(deleteByIndex2));
        assertFalse(deleteByIndex1.equals(deleteByIndex1DifferentCompany));
        assertFalse(deleteByName1.equals(deleteByIndex1));
        assertFalse(deleteByIndex1.equals(deleteByName1));
    }

    @Test
    public void toStringMethod() {
        RoleName roleName = new RoleName("Software Engineer");
        CompanyName companyName = new CompanyName("Google");
        DeleteCompanyRoleCommand deleteByNameCommand = new DeleteCompanyRoleCommand(roleName, companyName);

        String expectedByName = DeleteCompanyRoleCommand.class.getCanonicalName()
                + "{roleIndex=null, roleName=" + roleName + ", companyName=" + companyName + "}";

        assertEquals(expectedByName, deleteByNameCommand.toString());

        Index index = Index.fromOneBased(1);

        DeleteCompanyRoleCommand deleteByIndexCommand = new DeleteCompanyRoleCommand(index, companyName);
        String expectedByIndex = DeleteCompanyRoleCommand.class.getCanonicalName()
                + "{roleIndex=" + index + ", roleName=null, companyName=" + companyName + "}";

        assertEquals(expectedByIndex, deleteByIndexCommand.toString());
    }
}
