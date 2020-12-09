package BLL;

import BE.InputAlert;
import BE.Playlist;
import BE.Song;
import DAL.DAO.DB.PlaylistDBDAO;
import DAL.DAO.FILE.PlaylistLocalDAO;
import DAL.DAO.PlaylistDAOInterface;
import GUI.CONTROLLER.MainViewController;

import java.util.List;

public class PlaylistManager {
    protected static PlaylistDAOInterface playlistDAO;
    private static InputAlert inputAlert = new InputAlert();
    protected MainViewController mainController;


    /**
     *
     */
    static {
        try {
            playlistDAO = new PlaylistDBDAO();
        } catch (Exception e) {
            playlistDAO = new PlaylistLocalDAO();
            inputAlert.showAlert("Couldn't establish connection to the database. Your changes will be done locally.");
        }
    }

    /**
     * Save playlists locally instead of to database.
     */
    public void goLocal() {
        inputAlert.showAlert("Couldn't establish connection to the database. Your changes will be done locally.");
        playlistDAO = new PlaylistLocalDAO();
        mainController.load();
    }

    /**
     * Set the value of PlaylistDAO
     *
     * @param playlistDAO new value of PlaylistDAO
     */
    public void setPlaylistDAO(PlaylistDAOInterface playlistDAO) {
        PlaylistManager.playlistDAO = playlistDAO;
    }

    /**
     *
     */
    public PlaylistManager() {
        playlistDAO.setPlaylistManager(this);
    }

    /**
     * Set the value of mainController
     *
     * @param mainController new value of mainController
     */
    public void setMainController(MainViewController mainController) {
        this.mainController = mainController;
    }

    /**
     * loads the playlists, if it cannot connect to the database, it saves locally
     *
     * @return Playlists
     */
    public List<Playlist> loadPlaylists() throws Exception {
        return playlistDAO.loadPlaylist();
    }

    /**
     * Sends information to create playlist
     *
     * @param name
     * @throws Exception
     */
    public void createPlaylist(String name) throws Exception {
        playlistDAO.createPlaylist(name);
    }

    /**
     * Get the value of playlist name
     *
     * @param name new value of name
     * @return the value of name
     * @throws Exception
     */
    public Playlist getPlaylist(String name) throws Exception {
        return playlistDAO.getPlaylist(name);
    }

    /**
     * Sends information to delete playlist
     *
     * @param name
     * @throws Exception
     */
    public void deletePlaylist(String name) throws Exception {
        playlistDAO.deletePlaylist(name);
    }

    /**
     * @param playlist_id
     * @return
     * @throws Exception
     */
    public List<Song> loadSongsOnPlaylist(int playlist_id) throws Exception {
        return playlistDAO.loadSongsFromPlaylist(playlist_id);
    }

    /**
     * Sends information to add a song to playlist
     *
     * @param playlist_id
     * @param song_id
     * @throws Exception
     */
    public void addSongsToPlaylist(int playlist_id, int song_id) throws Exception {
        playlistDAO.AddSongToPlaylist(playlist_id, song_id);
    }

    /**
     * Sends information to delete a song from playlist
     *
     * @param playlist_id
     * @param song_id
     * @throws Exception
     */
    public void deleteSongFromPlaylist(int playlist_id, int song_id) throws Exception {
        playlistDAO.deleteFromPlaylist(playlist_id, song_id);
    }

    /**
     * Sends information to update playlist
     *
     * @param playlist
     * @throws Exception
     */
    public void updatePlaylist(Playlist playlist) throws Exception {
        playlistDAO.updatePlaylist(playlist);
    }
}
