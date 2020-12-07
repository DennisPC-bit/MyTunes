package DAL.DAO;

import BE.Song;
import BLL.SongManager;
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
     * @param name
     * @param path
     * @return
     * @throws Exception
     */
    boolean createSong(String name, String path) throws Exception;

    /**
     *
     * @param name
     * @param path
     * @param categoryId
     * @return
     * @throws Exception
     */
    boolean createSong(String name, String path, int categoryId) throws Exception;

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
     * @return
     * @throws Exception
     */
    boolean deleteSong(int id) throws Exception;

    /**
     *
     * @param id
     * @param modified
     * @return
     * @throws Exception
     */
    boolean updateSong(int id, Song modified) throws Exception;
}