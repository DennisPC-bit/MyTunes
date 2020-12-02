package BLL;

import BE.Song;
import DAL.DAO.DB.SongDBDAO;
import GUI.Controller;

import java.util.List;

public class SongManager {
    protected static final SongDBDAO songDBDAO = new SongDBDAO();
    protected Controller mainController;

    public SongManager(){
        songDBDAO.setSongManager(this);
    }

    public void setMainController(Controller mainController){
        this.mainController=mainController;
    }

    public static List<Song> loadSongs(){
        return songDBDAO.loadSongs();
    }

    public void createSong(String name, String path){
        songDBDAO.createSong(name,path);
    }

    public void getSong(String name){
        songDBDAO.getSong(name);
    }

    public void deleteSong(String name){
        songDBDAO.deleteSong(name);
    }
}
