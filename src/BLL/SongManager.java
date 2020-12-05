package BLL;

import BE.InputAlert;
import BE.Song;
import DAL.DAO.DB.SongDBDAO;
import DAL.DAO.FILE.SongLocalDAO;
import DAL.DAO.SongDAOInterface;
import GUI.CONTROLLER.MainViewController;

import java.util.List;

public class SongManager {
    private static InputAlert inputAlert = new InputAlert();
    protected static SongDAOInterface songDAO;

    static {
        try {
            songDAO = new SongDBDAO();
        } catch (Exception e) {
            songDAO = new SongLocalDAO();
        }
    }

    protected MainViewController mainController;

    public SongManager() {
        songDAO.setSongManager(this);
    }

    public void setSongDAO(SongDAOInterface songDAO) {
        SongManager.songDAO = songDAO;
    }

    public void setMainController(MainViewController mainController) {
        this.mainController = mainController;
    }

    public static List<Song> loadSongs() throws Exception {
            return songDAO.loadSongs();
    }

    public void createSong(String name, String path) throws Exception {
        songDAO.createSong(name, path);
    }

    public Song getSong(String name) throws Exception{
        return songDAO.getSong(name);
    }

    public void updateSong(int id, Song modified) throws Exception{
        songDAO.updateSong(id, modified);
    }

    public void deleteSong(int id) throws Exception {
        songDAO.deleteSong(id);
    }
}
