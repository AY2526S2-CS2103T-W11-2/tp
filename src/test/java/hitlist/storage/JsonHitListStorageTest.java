package hitlist.storage;

import static hitlist.testutil.Assert.assertThrows;
import static hitlist.testutil.TypicalPersons.ALICE;
import static hitlist.testutil.TypicalPersons.HOON;
import static hitlist.testutil.TypicalPersons.IDA;
import static hitlist.testutil.TypicalPersons.getTypicalHitList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import hitlist.commons.exceptions.DataLoadingException;
import hitlist.model.HitList;
import hitlist.model.ReadOnlyHitList;

public class JsonHitListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonHitListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readHitList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readHitList(null));
    }

    private java.util.Optional<ReadOnlyHitList> readHitList(String filePath) throws Exception {
        return new JsonHitListStorage(Paths.get(filePath)).readHitList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readHitList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readHitList("notJsonFormatHitList.json"));
    }

    @Test
    public void readHitList_invalidPersonHitList_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readHitList("invalidPersonHitList.json"));
    }

    @Test
    public void readHitList_invalidAndValidPersonHitList_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readHitList("invalidAndValidPersonHitList.json"));
    }

    @Test
    public void readAndSaveHitList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempHitList.json");
        HitList original = getTypicalHitList();
        JsonHitListStorage jsonHitListStorage = new JsonHitListStorage(filePath);

        // Save in new file and read back
        jsonHitListStorage.saveHitList(original, filePath);
        ReadOnlyHitList readBack = jsonHitListStorage.readHitList(filePath).get();
        assertEquals(original, new HitList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonHitListStorage.saveHitList(original, filePath);
        readBack = jsonHitListStorage.readHitList(filePath).get();
        assertEquals(original, new HitList(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonHitListStorage.saveHitList(original); // file path not specified
        readBack = jsonHitListStorage.readHitList().get(); // file path not specified
        assertEquals(original, new HitList(readBack));

    }

    @Test
    public void saveHitList_nullHitList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveHitList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code HitList} at the specified {@code filePath}.
     */
    private void saveHitList(ReadOnlyHitList hitList, String filePath) {
        try {
            new JsonHitListStorage(Paths.get(filePath))
                    .saveHitList(hitList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveHitList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveHitList(new HitList(), null));
    }
}
