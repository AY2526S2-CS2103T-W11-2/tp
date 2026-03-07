package hitlist.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import hitlist.commons.exceptions.DataLoadingException;
import hitlist.model.ReadOnlyHitList;
import hitlist.model.ReadOnlyUserPrefs;
import hitlist.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends HitListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getHitListFilePath();

    @Override
    Optional<ReadOnlyHitList> readHitList() throws DataLoadingException;

    @Override
    void saveHitList(ReadOnlyHitList addressBook) throws IOException;

}
