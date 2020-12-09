package DAL.DAO;

import BE.Song;
import BLL.SongManager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SongDAOInterface {
    /**
     * Sets the song manager
     *
     * @param songManager
     */
    void setSongManager(SongManager songManager);

    /**
     * Loads all songs
     *
     * @return A list of songs
     * @throws Exception If something went wrong
     */
    List<Song> loadSongs() throws Exception;

    /**
     * Creates a new song
     *
     * @param song the new song
     * @throws Exception If something went wrong
     */
    void createSong(Song song) throws Exception;

    /**
     * Gets a song
     *
     * @param name the name of teh song you wanted
     * @return the song
     * @throws Exception If something went wrong
     */
    Song getSong(String name) throws Exception;

    /**
     * Deletes a song
     *
     * @param id the id of the song you want to delete
     * @throws Exception If something went wrong
     */
    void deleteSong(int id) throws Exception;

    /**
     * @param id
     * @param modified
     * @throws Exception
     */
    void updateSong(int id, Song modified) throws Exception;

    List<Song> searchSong(String name) throws IOException;

    Map<Integer, String> getGenres() throws Exception;
}