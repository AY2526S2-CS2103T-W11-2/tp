package hitlist.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import hitlist.commons.exceptions.DataLoadingException;
import hitlist.model.HitList;
import hitlist.model.ReadOnlyHitList;

/**
 * Represents a storage for {@link HitList}.
 */
public interface HitListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getHitListFilePath();

    /**
     * Returns HitList data as a {@link ReadOnlyHitList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyHitList> readHitList() throws DataLoadingException;

    /**
     * @see #getHitListFilePath()
     */
    Optional<ReadOnlyHitList> readHitList(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyHitList} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveHitList(ReadOnlyHitList addressBook) throws IOException;

    /**
     * @see #saveHitList(ReadOnlyHitList)
     */
    void saveHitList(ReadOnlyHitList addressBook, Path filePath) throws IOException;

}
