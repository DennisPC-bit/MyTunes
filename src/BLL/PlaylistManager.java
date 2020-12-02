package BLL;

import BE.Playlist;
import DAL.DAO.DB.PlaylistDBDAO;
import GUI.CONTROLLER.MainViewController;

import java.sql.SQLException;
import java.util.List;

public class PlaylistManager {
    protected static final PlaylistDBDAO playlistDBDAO = new PlaylistDBDAO();
    protected MainViewController mainController;

    public PlaylistManager(){
        playlistDBDAO.setPlaylistManager(this);
    }

    public void setMainController(MainViewController mainController){
        this.mainController=mainController;
    }

    public static List<Playlist> loadPlaylists() throws SQLException {
        return playlistDBDAO.loadPlaylist();
    }

    public void createPlaylist(String name) throws SQLException {
        playlistDBDAO.createPlaylist(name);
    }

    public Playlist getPlaylist(String name) throws SQLException {
        return playlistDBDAO.getPlaylist(name);
    }

    public void deletePlaylist(String name) throws SQLException {
        playlistDBDAO.deletePlaylist(name);
    }
}
