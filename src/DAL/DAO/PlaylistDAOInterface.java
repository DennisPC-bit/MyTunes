package DAL.DAO;

import BE.Playlist;
import BE.Song;
import BLL.PlaylistManager;
import java.util.List;

public interface PlaylistDAOInterface {

    void setPlaylistManager(PlaylistManager playlistManager);

    List<Playlist> loadPlaylist() throws Exception;

    void createPlaylist(String name) throws Exception;

    Playlist getPlaylist(String name) throws Exception;

    void deletePlaylist(String name) throws Exception;

    List<Song> loadSongsFromPlaylist(int playlist_id) throws Exception;

    void AddSongToPlaylist(int playlist_id,int song_id) throws Exception;

    void deleteFromPlaylist(int playlist_id,int song_id) throws Exception;

    void updatePlaylist(Playlist playlist) throws Exception;
}
