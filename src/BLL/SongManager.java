package BLL;

import BE.Song;
import DAL.DAO.DB.SongDBDAO;
import GUI.CONTROLLER.MainViewController;

import java.sql.SQLException;
import java.util.List;

public class SongManager {
    protected static final SongDBDAO songDBDAO = new SongDBDAO();
    protected MainViewController mainController;

    public SongManager() {
        songDBDAO.setSongManager(this);
    }

    public void setMainController(MainViewController mainController) {
        this.mainController = mainController;
    }

    public static List<Song> loadSongs() {
        return songDBDAO.loadSongs();
    }

    public void createSong(String name, String path) throws SQLException {
        songDBDAO.createSong(name, path);
    }

    public Song getSong(String name) {
        return songDBDAO.getSong(name);
    }

    public void deleteSong(String name) {
        songDBDAO.deleteSong(name);
    }
}
