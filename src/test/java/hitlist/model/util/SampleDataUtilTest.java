package hitlist.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import hitlist.model.ReadOnlyHitList;
import hitlist.model.company.Company;
import hitlist.model.person.Person;
import hitlist.model.tag.Tag;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_returnsValidPersons() {
        Person[] persons = SampleDataUtil.getSamplePersons();

        assertEquals(6, persons.length);

        // Validate each sample person has non-empty required fields and optional fields present in sample data.
        for (Person person : persons) {
            assertTrue(person.getName().toString().length() > 0);
            assertTrue(person.getPhone().toString().length() > 0);

            Optional<?> email = person.getEmail();
            Optional<?> address = person.getAddress();
            assertTrue(email.isPresent());
            assertTrue(address.isPresent());
        }
    }

    @Test
    public void getSampleHitList_returnsValidHitList() {
        ReadOnlyHitList hitList = SampleDataUtil.getSampleHitList();
        assertNotNull(hitList);
    }

    @Test
    public void getSampleCompanies_returnsValidCompanies() {
        Company[] companies = SampleDataUtil.getSampleCompanies();

        assertEquals(6, companies.length);

        for (Company company : companies) {
            assertTrue(company.getName().toString().length() > 0);
            assertTrue(company.getDescription().toString().length() > 0);
        }
    }

    @Test
    public void getSampleHitList_containsAllSamplePersonsAndCompanies() {
        ReadOnlyHitList sampleHitList = SampleDataUtil.getSampleHitList();

        assertEquals(SampleDataUtil.getSamplePersons().length, sampleHitList.getPersonList().size());
        assertEquals(SampleDataUtil.getSampleCompanies().length, sampleHitList.getCompanyList().size());
    }

    @Test
    public void getTagSet_withUniqueTags_returnsCorrectSize() {
        Set<Tag> tags = SampleDataUtil.getTagSet("friends", "colleagues", "family");

        assertEquals(3, tags.size());
    }

    @Test
    public void getTagSet_withDuplicateTags_deduplicatesCorrectly() {
        Set<Tag> tags = SampleDataUtil.getTagSet("friends", "friends", "family");

        // Set semantics should remove duplicates.
        assertEquals(2, tags.size());
    }

    @Test
    public void getTagSet_withNoTags_returnsEmptySet() {
        Set<Tag> tags = SampleDataUtil.getTagSet();

        assertTrue(tags.isEmpty());
    }
}
