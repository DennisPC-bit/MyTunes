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

    public void goLocal(){
        songDAO = new SongLocalDAO();
        mainController.load();
    }

    protected MainViewController mainController;

    /**
     *
     */
    public SongManager() {
        songDAO.setSongManager(this);
    }

    /**
     * Set the value of songDAO
     * @param songDAO new value of songDAO
     */
    public void setSongDAO(SongDAOInterface songDAO) {
        SongManager.songDAO = songDAO;
    }

    /**
     * Set he value of MainController
     * @param mainController new value of MainController
     */
    public void setMainController(MainViewController mainController) {
        this.mainController = mainController;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public List<Song> loadSongs() throws Exception{
        return songDAO.loadSongs();
    }

    /**
     * Sends information to create a song
     * @param song
     * @throws Exception
     */
    public void createSong(Song song) throws Exception {
        songDAO.createSong(song);
    }

    /**
     * Get the value of song name
     * @param name new value of song name
     * @return the value of song name
     * @throws Exception
     */
    public Song getSong(String name) throws Exception{
        return songDAO.getSong(name);
    }

    /**
     * Sends information to update song
     * @param id
     * @param modified
     * @throws Exception
     */
    public void updateSong(int id, Song modified) throws Exception{
        songDAO.updateSong(id, modified);
    }

    /**
     * Sends information to delete song
     * @param id
     * @throws Exception
     */
    public void deleteSong(int id) throws Exception {
        songDAO.deleteSong(id);
    }

    /**
     *
     * @param search
     * @return
     * @throws Exception
     */
    public List<Song> searchSong(String search) throws Exception{
        return songDAO.searchSong(search);
    }
}
