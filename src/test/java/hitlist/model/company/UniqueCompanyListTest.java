package hitlist.model.company;

import static hitlist.logic.commands.CommandTestUtil.VALID_COMPANY_DESCRIPTION_META;
import static hitlist.logic.commands.CommandTestUtil.VALID_COMPANY_NAME_GOOGLE;
import static hitlist.testutil.Assert.assertThrows;
import static hitlist.testutil.TypicalCompanies.GOOGLE;
import static hitlist.testutil.TypicalCompanies.META;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import hitlist.model.company.exceptions.CompanyNotFoundException;
import hitlist.model.company.exceptions.DuplicateCompanyException;
import hitlist.testutil.CompanyBuilder;

public class UniqueCompanyListTest {

    private final UniqueCompanyList uniqueCompanyList = new UniqueCompanyList();

    @Test
    public void contains_nullCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCompanyList.contains(null));
    }

    @Test
    public void contains_companyNotInList_returnsFalse() {
        assertFalse(uniqueCompanyList.contains(GOOGLE));
    }

    @Test
    public void contains_companyInList_returnsTrue() {
        uniqueCompanyList.add(GOOGLE);
        assertTrue(uniqueCompanyList.contains(GOOGLE));
    }

    @Test
    public void contains_companyWithDifferentCompanyDescriptionInList_returnsTrue() {
        uniqueCompanyList.add(GOOGLE);
        Company editedGoogle = new CompanyBuilder()
                .withName(VALID_COMPANY_NAME_GOOGLE)
                .withDescription(VALID_COMPANY_DESCRIPTION_META)
                .build();
        assertTrue(uniqueCompanyList.contains(editedGoogle));
    }

    @Test
    public void add_nullCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCompanyList.add(null));
    }

    @Test
    public void add_duplicateCompany_throwsDuplicateCompanyException() {
        uniqueCompanyList.add(GOOGLE);
        assertThrows(DuplicateCompanyException.class, () -> uniqueCompanyList.add(GOOGLE));
    }

    @Test
    public void setCompany_nullTargetCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCompanyList.setCompany(null, GOOGLE));
    }

    @Test
    public void setCompany_targetCompanyNotInList_throwsCompanyNotFoundException() {
        assertThrows(CompanyNotFoundException.class, () -> uniqueCompanyList.setCompany(GOOGLE, GOOGLE));
    }

    @Test
    public void setCompany_editedCompanyIsSameCompany_success() {
        uniqueCompanyList.add(GOOGLE);
        uniqueCompanyList.setCompany(GOOGLE, GOOGLE);
        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(GOOGLE);
        assertEquals(uniqueCompanyList, expectedUniqueCompanyList);
    }

    @Test
    public void setCompany_editedCompanyHasSameIdentity_success() {
        uniqueCompanyList.add(GOOGLE);
        Company editedGoogle = new CompanyBuilder()
                .withName(VALID_COMPANY_NAME_GOOGLE)
                .withDescription(VALID_COMPANY_DESCRIPTION_META)
                .build();
        uniqueCompanyList.setCompany(GOOGLE, editedGoogle);
        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(editedGoogle);
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompany_editedCompanyHasDifferentIdentity_success() {
        uniqueCompanyList.add(GOOGLE);
        uniqueCompanyList.setCompany(GOOGLE, META);
        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(META);
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompany_editedCompanyHasNonUniqueIdentity_throwsDuplicateCompanyException() {
        uniqueCompanyList.add(GOOGLE);
        uniqueCompanyList.add(META);
        assertThrows(DuplicateCompanyException.class, () -> uniqueCompanyList.setCompany(GOOGLE, META));
    }

    @Test
    public void remove_nullCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCompanyList.remove(null));
    }

    @Test
    public void remove_companyDoesNotExist_throwsCompanyNotFoundException() {
        assertThrows(CompanyNotFoundException.class, () -> uniqueCompanyList.remove(GOOGLE));
    }

    @Test
    public void remove_existingCompany_removesCompany() {
        uniqueCompanyList.add(GOOGLE);
        uniqueCompanyList.remove(GOOGLE);
        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompanies_nullUniqueCompanyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
                -> uniqueCompanyList.setCompanies((UniqueCompanyList) null));
    }

    @Test
    public void setCompanies_uniqueCompanyList_replacesOwnListWithProvidedUniqueCompanyList() {
        uniqueCompanyList.add(GOOGLE);
        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(META);
        uniqueCompanyList.setCompanies(expectedUniqueCompanyList);
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompanies_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
                -> uniqueCompanyList.setCompanies((List<Company>) null));
    }

    @Test
    public void setCompanies_list_replacesOwnListWithProvidedList() {
        uniqueCompanyList.add(GOOGLE);
        List<Company> companyList = List.of(META);
        uniqueCompanyList.setCompanies(companyList);
        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(META);
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompanies_listWithDuplicateCompanies_throwsDuplicateCompanyException() {
        List<Company> listWithDuplicateCompanies = List.of(GOOGLE, GOOGLE);
        assertThrows(DuplicateCompanyException.class, ()
                -> uniqueCompanyList.setCompanies(listWithDuplicateCompanies));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueCompanyList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void iterator_defaultList_isNotNull() {
        assertNotNull(uniqueCompanyList.iterator());
    }

    @Test
    public void iterator_populatedList_iteratesInOrder() {
        uniqueCompanyList.add(GOOGLE);
        uniqueCompanyList.add(META);

        Iterator<Company> iterator = uniqueCompanyList.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(GOOGLE, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(META, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(uniqueCompanyList.equals(uniqueCompanyList));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(uniqueCompanyList.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(uniqueCompanyList.equals(5)); // Passing an arbitrary different type
    }

    @Test
    public void equals_identicalLists_returnsTrue() {
        uniqueCompanyList.add(GOOGLE);

        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(GOOGLE);

        assertTrue(uniqueCompanyList.equals(expectedUniqueCompanyList));
    }

    @Test
    public void equals_differentLists_returnsFalse() {
        uniqueCompanyList.add(GOOGLE);

        UniqueCompanyList otherUniqueCompanyList = new UniqueCompanyList();
        otherUniqueCompanyList.add(META);

        assertFalse(uniqueCompanyList.equals(otherUniqueCompanyList));
    }

    @Test
    public void hashCode_identicalLists_haveSameHashCode() {
        uniqueCompanyList.add(GOOGLE);

        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(GOOGLE);

        assertEquals(uniqueCompanyList.hashCode(), expectedUniqueCompanyList.hashCode());
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueCompanyList.asUnmodifiableObservableList().toString(), uniqueCompanyList.toString());
    }

    @Test
    public void setCompany_editedCompanyIsStrictlySameCompany_success() {
        uniqueCompanyList.add(GOOGLE);
        uniqueCompanyList.setCompany(GOOGLE, GOOGLE);

        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(GOOGLE);
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompany_editedCompanyIsDifferentAndNotContained_success() {
        uniqueCompanyList.add(GOOGLE);
        uniqueCompanyList.setCompany(GOOGLE, META);

        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(META);
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompany_nullEditedCompanyThrowsNullPointerException() {
        uniqueCompanyList.add(GOOGLE);
        assertThrows(NullPointerException.class, () -> uniqueCompanyList.setCompany(GOOGLE, null));
    }

    @Test
    public void setCompanies_listWithMultipleUniqueCompanies_success() {
        List<Company> listWithUniqueCompanies = List.of(GOOGLE, META);
        uniqueCompanyList.setCompanies(listWithUniqueCompanies);

        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(GOOGLE);
        expectedUniqueCompanyList.add(META);
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompanies_listWithDuplicatesSpacedOut_throwsDuplicateCompanyException() {
        Company anotherGoogle = new CompanyBuilder(GOOGLE).build();
        List<Company> listWithDuplicates = List.of(META, GOOGLE, anotherGoogle);

        assertThrows(DuplicateCompanyException.class, () -> uniqueCompanyList.setCompanies(listWithDuplicates));
    }

    @Test
    public void setCompanies_listWithSingleCompany_success() {
        List<Company> singleCompanyList = List.of(GOOGLE);
        uniqueCompanyList.setCompanies(singleCompanyList);

        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(GOOGLE);
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompany_sameIdentity_replacesSuccessfully() {
        UniqueCompanyList list = new UniqueCompanyList();
        Company target = new CompanyBuilder().withName("Google Inc.").withDescription("Desc A").build();
        Company edited = new CompanyBuilder().withName("Google Inc.").withDescription("Desc B").build();
        list.add(target);

        list.setCompany(target, edited);

        assertTrue(list.contains(edited));
    }

    @Test
    public void equals_branches() {
        UniqueCompanyList list = new UniqueCompanyList();
        list.add(new CompanyBuilder().withName("Google Inc.").build());

        assertTrue(list.equals(list));
        assertFalse(list.equals(null));
        assertFalse(list.equals(1));
    }

    @Test
    public void toString_nonEmptyList_containsCount() {
        UniqueCompanyList list = new UniqueCompanyList();
        list.add(new CompanyBuilder()
                .withName("Google Inc.")
                .withDescription("Valid Description").build());

        assertTrue(list.toString().contains("Google Inc."));
    }
}
