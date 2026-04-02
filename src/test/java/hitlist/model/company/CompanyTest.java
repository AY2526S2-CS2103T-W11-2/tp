package hitlist.model.company;

import static hitlist.logic.commands.CommandTestUtil.INVALID_COMPANY_DESCRIPTION;
import static hitlist.logic.commands.CommandTestUtil.INVALID_COMPANY_NAME;
import static hitlist.logic.commands.CommandTestUtil.VALID_COMPANY_DESCRIPTION_GOOGLE;
import static hitlist.logic.commands.CommandTestUtil.VALID_COMPANY_DESCRIPTION_META;
import static hitlist.logic.commands.CommandTestUtil.VALID_COMPANY_NAME_GOOGLE;
import static hitlist.logic.commands.CommandTestUtil.VALID_COMPANY_NAME_META;
import static hitlist.testutil.Assert.assertThrows;
import static hitlist.testutil.TypicalRoles.PRODUCT_MANAGER;
import static hitlist.testutil.TypicalRoles.SOFTWARE_ENGINEER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hitlist.model.company.role.UniqueRoleList;
import hitlist.testutil.CompanyBuilder;

public class CompanyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Company(null, null));
    }

    @Test
    public void constructor_invalidCompany_throwsIllegalArgumentException() {
        String invalidName = "";
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () ->
                new Company(
                        new CompanyName(invalidName),
                        new CompanyDescription(invalidDescription)));
    }

    @Test
    public void constructor_invalidCompanyName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Company(
                        new CompanyName(INVALID_COMPANY_NAME),
                        new CompanyDescription(VALID_COMPANY_DESCRIPTION_GOOGLE)));
    }

    @Test
    public void constructor_invalidCompanyDescription_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Company(
                        new CompanyName(VALID_COMPANY_NAME_GOOGLE),
                        new CompanyDescription(INVALID_COMPANY_DESCRIPTION)));
    }

    @Test
    public void constructor_nullUniqueRoleList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Company(
                        new CompanyName(VALID_COMPANY_NAME_GOOGLE),
                        new CompanyDescription(VALID_COMPANY_DESCRIPTION_GOOGLE),
                        null));
    }

    @Test
    public void constructor_validCompany_success() {
        UniqueRoleList validRoleList = new UniqueRoleList();
        validRoleList.add(PRODUCT_MANAGER);
        validRoleList.add(SOFTWARE_ENGINEER);
        Company company = new CompanyBuilder()
                .withName(VALID_COMPANY_NAME_GOOGLE)
                .withDescription(VALID_COMPANY_DESCRIPTION_GOOGLE)
                .withUniqueRoleList(validRoleList)
                .build();
        assertEquals(company.getUniqueRoleList(), validRoleList);
    }

    @Test
    public void getName() {
        Company company = new CompanyBuilder().withName(VALID_COMPANY_NAME_GOOGLE).build();
        assertEquals(company.getName(), new CompanyName("Google Inc."));
    }

    @Test
    public void getDescription() {
        Company company = new CompanyBuilder().withDescription("Valid Company Description").build();
        assertEquals(company.getDescription(), new CompanyDescription("Valid Company Description"));
    }

    @Test
    public void getUniqueRoleList() {
        UniqueRoleList validRoleList = new UniqueRoleList();
        validRoleList.add(PRODUCT_MANAGER);
        validRoleList.add(SOFTWARE_ENGINEER);
        Company company = new CompanyBuilder().withUniqueRoleList(validRoleList).build();
        assertEquals(company.getUniqueRoleList(), validRoleList);
    }

    @Test
    public void isSameCompany() {
        Company company = new CompanyBuilder()
                .withName(VALID_COMPANY_NAME_GOOGLE)
                .withDescription(VALID_COMPANY_DESCRIPTION_GOOGLE)
                .build();

        // same object -> returns true
        assertTrue(company.isSameCompany(company));

        // different description -> returns true
        assertTrue(company.isSameCompany(
                new CompanyBuilder()
                        .withName(VALID_COMPANY_NAME_GOOGLE)
                        .withDescription(VALID_COMPANY_DESCRIPTION_META)
                        .build()));

        // null -> returns false
        assertFalse(company.isSameCompany(null));

        // different name and description -> returns false
        assertFalse(company.isSameCompany(
                new CompanyBuilder()
                        .withName(VALID_COMPANY_NAME_META)
                        .withDescription(VALID_COMPANY_DESCRIPTION_META)
                        .build()));

        // different name -> returns false
        assertFalse(company.isSameCompany(
                new CompanyBuilder()
                        .withName(VALID_COMPANY_NAME_META)
                        .withDescription(VALID_COMPANY_DESCRIPTION_GOOGLE)
                        .build()));
    }

    @Test
    public void equals() {
        UniqueRoleList roleList = new UniqueRoleList();
        roleList.add(PRODUCT_MANAGER);

        Company company = new CompanyBuilder()
                .withName("Valid Company Name")
                .withDescription("Valid Company Description")
                .withUniqueRoleList(new UniqueRoleList())
                .build();

        // same values -> returns true
        assertTrue(company.equals(
                new CompanyBuilder()
                        .withName("Valid Company Name")
                        .withDescription("Valid Company Description")
                        .withUniqueRoleList(new UniqueRoleList())
                        .build()));

        // same object -> returns true
        assertTrue(company.equals(company));

        // null -> returns false
        assertFalse(company.equals(null));

        // different types -> returns false
        assertFalse(company.equals(5.0f));

        // different values -> returns false
        assertFalse(company.equals(
                new CompanyBuilder()
                        .withName("Other Valid Company Name")
                        .withDescription("Other Valid Company Description")
                        .withUniqueRoleList(roleList)
                        .build()));
    }

    @Test
    public void hashCodeTest() {
        Company company1 = new CompanyBuilder()
                .withName("Valid Company Name")
                .withDescription("Valid Company Description")
                .build();
        Company company2 = new CompanyBuilder()
                .withName("Valid Company Name")
                .withDescription("Valid Company Description")
                .build();
        Company company3 = new CompanyBuilder()
                .withName("Other Valid Company Name")
                .withDescription("Other Valid Company Description")
                .build();

        // same values -> returns same hash code
        assertEquals(company1.hashCode(), company2.hashCode());

        // different values -> returns different hash code
        assertFalse(company1.hashCode() == company3.hashCode());
    }

    @Test
    public void toStringTest() {
        Company company = new CompanyBuilder()
                .withName("Valid Company Name")
                .withDescription("Valid Company Description")
                .build();
        String expectedString = "companyName=Valid Company Name, "
                + "companyDescription=Valid Company Description, "
                + "roles=[]";
        assertEquals(company.toString(), expectedString);
    }

    @Test
    public void toStringTestWithRoles() {
        UniqueRoleList validRoleList = new UniqueRoleList();
        validRoleList.add(PRODUCT_MANAGER);
        Company company = new CompanyBuilder().withName("Valid Company Name")
                .withDescription("Valid Company Description")
                .withUniqueRoleList(validRoleList)
                .build();
        String expectedString = "companyName=Valid Company Name, "
                + "companyDescription=Valid Company Description, "
                + "roles=[role=Product Manager, description=Responsible for overseeing the development and delivery of "
                + "a product, ensuring it meets customer needs and business goals.]";
        assertEquals(company.toString(), expectedString);
    }

    // Role-level operations tests

    @Test
    public void hasRole_nullRole_throwsNullPointerException() {
        Company company = new CompanyBuilder().build();
        assertThrows(NullPointerException.class, () -> company.hasRole(null));
    }

    @Test
    public void hasRole_roleExists_returnsTrue() {
        Company company = new CompanyBuilder().build();
        company.addRole(PRODUCT_MANAGER);

        assertTrue(company.hasRole(PRODUCT_MANAGER));
    }

    @Test
    public void hasRole_roleDoesNotExist_returnsFalse() {
        Company company = new CompanyBuilder().build();
        company.addRole(PRODUCT_MANAGER);

        assertFalse(company.hasRole(SOFTWARE_ENGINEER));
    }

    @Test
    public void hasRole_emptyRoleList_returnsFalse() {
        Company company = new CompanyBuilder().build();

        assertFalse(company.hasRole(PRODUCT_MANAGER));
    }

    @Test
    public void addRole_nullRole_throwsNullPointerException() {
        Company company = new CompanyBuilder().build();
        assertThrows(NullPointerException.class, () -> company.addRole(null));
    }

    @Test
    public void addRole_validRole_success() {
        Company company = new CompanyBuilder().build();
        company.addRole(PRODUCT_MANAGER);

        assertTrue(company.hasRole(PRODUCT_MANAGER));
    }

    @Test
    public void addRole_multipleRoles_success() {
        Company company = new CompanyBuilder().build();
        company.addRole(PRODUCT_MANAGER);
        company.addRole(SOFTWARE_ENGINEER);

        assertTrue(company.hasRole(PRODUCT_MANAGER));
        assertTrue(company.hasRole(SOFTWARE_ENGINEER));
    }

    @Test
    public void setRole_nullTarget_throwsNullPointerException() {
        Company company = new CompanyBuilder().build();
        company.addRole(PRODUCT_MANAGER);

        assertThrows(NullPointerException.class, () -> company.setRole(null, SOFTWARE_ENGINEER));
    }

    @Test
    public void setRole_nullEditedRole_throwsNullPointerException() {
        Company company = new CompanyBuilder().build();
        company.addRole(PRODUCT_MANAGER);

        assertThrows(NullPointerException.class, () -> company.setRole(PRODUCT_MANAGER, null));
    }

    @Test
    public void setRole_validRoles_success() {
        Company company = new CompanyBuilder().build();
        company.addRole(PRODUCT_MANAGER);

        assertTrue(company.hasRole(PRODUCT_MANAGER));

        company.setRole(PRODUCT_MANAGER, SOFTWARE_ENGINEER);

        assertFalse(company.hasRole(PRODUCT_MANAGER));
        assertTrue(company.hasRole(SOFTWARE_ENGINEER));
    }

    @Test
    public void removeRole_nullRole_throwsNullPointerException() {
        Company company = new CompanyBuilder().build();
        assertThrows(NullPointerException.class, () -> company.removeRole(null));
    }

    @Test
    public void removeRole_validRole_success() {
        Company company = new CompanyBuilder().build();
        company.addRole(PRODUCT_MANAGER);

        assertTrue(company.hasRole(PRODUCT_MANAGER));

        company.removeRole(PRODUCT_MANAGER);

        assertFalse(company.hasRole(PRODUCT_MANAGER));
    }

    @Test
    public void removeRole_roleFromMultipleRoles_success() {
        Company company = new CompanyBuilder().build();
        company.addRole(PRODUCT_MANAGER);
        company.addRole(SOFTWARE_ENGINEER);

        assertTrue(company.hasRole(PRODUCT_MANAGER));
        assertTrue(company.hasRole(SOFTWARE_ENGINEER));

        company.removeRole(PRODUCT_MANAGER);

        assertFalse(company.hasRole(PRODUCT_MANAGER));
        assertTrue(company.hasRole(SOFTWARE_ENGINEER));
    }

    @Test
    public void equals_withSameRoles_returnsTrue() {
        UniqueRoleList roleList = new UniqueRoleList();
        roleList.add(PRODUCT_MANAGER);

        Company company1 = new CompanyBuilder()
                .withName("Valid Company Name")
                .withDescription("Valid Company Description")
                .withUniqueRoleList(new UniqueRoleList())
                .build();
        company1.addRole(PRODUCT_MANAGER);

        Company company2 = new CompanyBuilder()
                .withName("Valid Company Name")
                .withDescription("Valid Company Description")
                .withUniqueRoleList(new UniqueRoleList())
                .build();
        company2.addRole(PRODUCT_MANAGER);

        assertTrue(company1.equals(company2));
    }

    @Test
    public void equals_withDifferentRoles_returnsFalse() {
        Company company1 = new CompanyBuilder()
                .withName("Valid Company Name")
                .withDescription("Valid Company Description")
                .withUniqueRoleList(new UniqueRoleList())
                .build();
        company1.addRole(PRODUCT_MANAGER);

        Company company2 = new CompanyBuilder()
                .withName("Valid Company Name")
                .withDescription("Valid Company Description")
                .withUniqueRoleList(new UniqueRoleList())
                .build();
        company2.addRole(SOFTWARE_ENGINEER);

        assertFalse(company1.equals(company2));
    }
}
