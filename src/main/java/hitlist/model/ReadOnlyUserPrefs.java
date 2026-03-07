package hitlist.model;

import java.nio.file.Path;

import hitlist.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getHitListFilePath();

}
