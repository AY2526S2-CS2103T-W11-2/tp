package hitlist.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import hitlist.commons.core.LogsCenter;
import hitlist.commons.exceptions.DataLoadingException;
import hitlist.model.ReadOnlyHitList;
import hitlist.model.ReadOnlyUserPrefs;
import hitlist.model.UserPrefs;

/**
 * Manages storage of HitList data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private HitListStorage hitListStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code HitListStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(HitListStorage hitListStorage, UserPrefsStorage userPrefsStorage) {
        this.hitListStorage = hitListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ HitList methods ==============================

    @Override
    public Path getHitListFilePath() {
        return hitListStorage.getHitListFilePath();
    }

    @Override
    public Optional<ReadOnlyHitList> readHitList() throws DataLoadingException {
        return readHitList(hitListStorage.getHitListFilePath());
    }

    @Override
    public Optional<ReadOnlyHitList> readHitList(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return hitListStorage.readHitList(filePath);
    }

    @Override
    public void saveHitList(ReadOnlyHitList addressBook) throws IOException {
        saveHitList(addressBook, hitListStorage.getHitListFilePath());
    }

    @Override
    public void saveHitList(ReadOnlyHitList addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        hitListStorage.saveHitList(addressBook, filePath);
    }

}
