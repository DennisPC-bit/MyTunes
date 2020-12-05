package BLL;

import BE.Song;
import DAL.DAO.DB.SongDBDAO;
import DAL.DAO.FILE.SongLocalDAO;
import DAL.DAO.SongDAOInterface;
import GUI.CONTROLLER.MainViewController;

import java.sql.SQLException;
import java.util.List;

public class SongManager {
    protected static SongDAOInterface songDAO = new SongDBDAO();
    protected MainViewController mainController;

    public SongManager() {
        songDAO.setSongManager(this);
    }

    public void setMainController(MainViewController mainController) {
        this.mainController = mainController;
    }

    public static List<Song> loadSongs() {
        try {
            return songDAO.loadSongs();
        } catch (NullPointerException e) {
            songDAO=new SongLocalDAO();
            try {
                return songDAO.loadSongs();
            } catch (Exception exception) {
                exception.printStackTrace();
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
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
