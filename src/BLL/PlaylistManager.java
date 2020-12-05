package BLL;

import BE.InputAlert;
import BE.Playlist;
import BE.Song;
import DAL.DAO.DB.PlaylistDBDAO;
import DAL.DAO.FILE.PlaylistLocalDAO;
import DAL.DAO.PlaylistDAOInterface;
import GUI.CONTROLLER.MainViewController;

import java.util.List;

public class PlaylistManager{
    protected static PlaylistDAOInterface playlistDAO;
    private static InputAlert inputAlert = new InputAlert();

    static {
        try {
            playlistDAO = new PlaylistDBDAO();
        } catch (Exception e) {
            playlistDAO = new PlaylistLocalDAO();
            inputAlert.showAlert(e.getMessage());
        }
    }


    protected MainViewController mainController;

    public void setPlaylistDAO(PlaylistDAOInterface playlistDAO) {
        PlaylistManager.playlistDAO = playlistDAO;
    }

    public PlaylistManager(){
        playlistDAO.setPlaylistManager(this);
    }

    public void setMainController(MainViewController mainController){
        this.mainController=mainController;
    }

    /**
     * loads the playlists, if it cannot connect to the database, it saves locally
     * @return Playlists
     */
    public static List<Playlist> loadPlaylists() throws Exception {
            return playlistDAO.loadPlaylist();
    }

    public void createPlaylist(String name) throws Exception {
        playlistDAO.createPlaylist(name);
    }

    public Playlist getPlaylist(String name) throws Exception {
        return playlistDAO.getPlaylist(name);
    }

    public void deletePlaylist(String name) throws Exception {
        playlistDAO.deletePlaylist(name);
    }

    public List<Song> loadSongsOnPlaylist(int playlist_id) throws Exception {
        return playlistDAO.loadSongsFromPlaylist(playlist_id);
    }

    public void addSongsToPlaylist(int playlist_id,int song_id) throws Exception {
        playlistDAO.AddSongToPlaylist(playlist_id,song_id);
    }

    public void deleteSongFromPlaylist(int playlist_id,int song_id) throws Exception {
        playlistDAO.deleteFromPlaylist(playlist_id, song_id);
    }

    public void updatePlaylist(Playlist playlist) throws Exception {
        playlistDAO.updatePlaylist(playlist);
    }
}
