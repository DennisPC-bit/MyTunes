package BLL;

import BE.Playlist;
import DAL.DAO.DB.PlaylistDBDAO;
import GUI.Controller;

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

    public static List<Playlist> loadPlaylists(){
        return playlistDBDAO.loadPlaylist();
    }

    public void createPlaylist(String name){
        playlistDBDAO.createPlaylist(name);
    }

    public Playlist getPlaylist(String name){
        return playlistDBDAO.getPlaylist(name);
    }

    public void deletePlaylist(String name){
        playlistDBDAO.deletePlaylist(name);
    }
}
