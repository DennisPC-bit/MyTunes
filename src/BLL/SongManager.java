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

    /**
     *
     */
    public SongManager() {
        songDAO.setSongManager(this);
    }

    /**
     *
     * @param songDAO
     */
    public void setSongDAO(SongDAOInterface songDAO) {
        SongManager.songDAO = songDAO;
    }

    /**
     *
     * @param mainController
     */
    public void setMainController(MainViewController mainController) {
        this.mainController = mainController;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public static List<Song> loadSongs() throws Exception {
            return songDAO.loadSongs();
    }

    /**
     *
     * @param name
     * @param path
     * @throws Exception
     */
    public void createSong(String name, String path) throws Exception {
        songDAO.createSong(name, path);
    }

    /**
     *
     * @param name
     * @return
     * @throws Exception
     */
    public Song getSong(String name) throws Exception{
        return songDAO.getSong(name);
    }

    /**
     *
     * @param id
     * @param modified
     * @throws Exception
     */
    public void updateSong(int id, Song modified) throws Exception{
        songDAO.updateSong(id, modified);
    }

    /**
     *
     * @param id
     * @throws Exception
     */
    public void deleteSong(int id) throws Exception {
        songDAO.deleteSong(id);
    }
}
