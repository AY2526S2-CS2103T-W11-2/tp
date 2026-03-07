package hitlist.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import hitlist.commons.core.LogsCenter;
import hitlist.commons.exceptions.DataLoadingException;
import hitlist.commons.exceptions.IllegalValueException;
import hitlist.commons.util.FileUtil;
import hitlist.commons.util.JsonUtil;
import hitlist.model.ReadOnlyHitList;

/**
 * A class to access HitList data stored as a json file on the hard disk.
 */
public class JsonHitListStorage implements HitListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonHitListStorage.class);

    private Path filePath;

    public JsonHitListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getHitListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyHitList> readHitList() throws DataLoadingException {
        return readHitList(filePath);
    }

    /**
     * Similar to {@link #readHitList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyHitList> readHitList(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableHitList> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableHitList.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveHitList(ReadOnlyHitList addressBook) throws IOException {
        saveHitList(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveHitList(ReadOnlyHitList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveHitList(ReadOnlyHitList addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableHitList(addressBook), filePath);
    }

}
