package DAL.DAO;

import BE.Playlist;
import BE.Song;
import BLL.PlaylistManager;
import java.util.List;

public interface PlaylistDAOInterface {

    /**
     *<
     * @param playlistManager
     */
    void setPlaylistManager(PlaylistManager playlistManager);

    /**
     *
     * @return
     * @throws Exception
     */
    List<Playlist> loadPlaylist() throws Exception;

    /**
     *
     * @param name
     * @throws Exception
     */
    void createPlaylist(String name) throws Exception;

    /**
     *
     * @param name
     * @return
     * @throws Exception
     */

    Playlist getPlaylist(String name) throws Exception;

    /**
     *
     * @param name
     * @throws Exception
     */

    void deletePlaylist(String name) throws Exception;

    /**
     *
     * @param playlist_id
     * @return
     * @throws Exception
     */
    List<Song> loadSongsFromPlaylist(int playlist_id) throws Exception;

    /**
     *
     * @param playlist_id
     * @param song_id
     * @throws Exception
     */

    void AddSongToPlaylist(int playlist_id,int song_id) throws Exception;

    /**
     *
     * @param playlist_id
     * @param song_id
     * @throws Exception
     */

    void deleteFromPlaylist(int playlist_id,int song_id) throws Exception;

    /**
     *
     * @param playlist
     * @throws Exception
     */

    void updatePlaylist(Playlist playlist) throws Exception;
}
