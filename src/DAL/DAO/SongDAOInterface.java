package DAL.DAO;

import BE.Song;
import BLL.SongManager;

import java.io.IOException;
import java.util.List;

public interface SongDAOInterface {
    void setSongManager(SongManager songManager);
    List<Song> loadSongs() throws Exception;
    boolean createSong(String name, String path) throws Exception;
    boolean createSong(String name, String path, int categoryId) throws Exception;
    Song getSong(String name) throws Exception;
    boolean deleteSong(int id) throws Exception;
    boolean updateSong(int id, Song modified) throws Exception;
    List<Song> searchSong(String name) throws IOException;
}