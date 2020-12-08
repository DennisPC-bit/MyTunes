package DAL.DAO;

import BE.Song;
import BLL.SongManager;

import java.io.IOException;
import java.util.List;

public interface SongDAOInterface {
    /**
     *
     * @param songManager
     */
    void setSongManager(SongManager songManager);

    /**
     *
     * @return
     * @throws Exception
     */
    List<Song> loadSongs() throws Exception;

    /**
     *
     * @param song the song
     * @throws Exception
     */
    void createSong(Song song) throws Exception;

    /**
     *
     * @param name
     * @return
     * @throws Exception
     */
    Song getSong(String name) throws Exception;

    /**
     *
     * @param id
     * @throws Exception
     */
    void deleteSong(int id) throws Exception;

    /**
     *
     * @param id
     * @param modified
     * @throws Exception
     */
    void updateSong(int id, Song modified) throws Exception;
    List<Song> searchSong(String name) throws IOException;
}