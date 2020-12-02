package BLL;

import BE.Playlist;
import DAL.DAO.DB.PlaylistDBDAO;
import GUI.Controller;

import java.sql.SQLException;
import java.util.List;

public class PlaylistManager {
    protected static final PlaylistDBDAO playlistDBDAO = new PlaylistDBDAO();
    protected Controller mainController;

    public PlaylistManager(){
        playlistDBDAO.setPlaylistManager(this);
    }

    public void setMainController(Controller mainController){
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
