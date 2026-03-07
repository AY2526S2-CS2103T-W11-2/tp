package hitlist.storage;

import static hitlist.testutil.TypicalPersons.getTypicalHitList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import hitlist.commons.core.GuiSettings;
import hitlist.model.HitList;
import hitlist.model.ReadOnlyHitList;
import hitlist.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonHitListStorage addressBookStorage = new JsonHitListStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonHitListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonHitListStorageTest} class.
         */
        HitList original = getTypicalHitList();
        storageManager.saveHitList(original);
        ReadOnlyHitList retrieved = storageManager.readHitList().get();
        assertEquals(original, new HitList(retrieved));
    }

    @Test
    public void getHitListFilePath() {
        assertNotNull(storageManager.getHitListFilePath());
    }

}
