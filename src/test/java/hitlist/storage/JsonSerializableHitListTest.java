package hitlist.storage;

import static hitlist.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import hitlist.commons.exceptions.IllegalValueException;
import hitlist.commons.util.JsonUtil;
import hitlist.model.HitList;
import hitlist.testutil.TypicalPersons;

public class JsonSerializableHitListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableHitListTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsHitList.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonHitList.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonHitList.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableHitList dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableHitList.class).get();
        HitList hitListFromFile = dataFromFile.toModelType();
        HitList typicalPersonsHitList = TypicalPersons.getTypicalHitList();
        assertEquals(hitListFromFile, typicalPersonsHitList);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableHitList dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableHitList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableHitList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableHitList.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableHitList.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
