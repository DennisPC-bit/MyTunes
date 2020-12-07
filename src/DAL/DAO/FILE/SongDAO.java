package DAL.DAO.FILE;

import BE.Song;
import DAL.DB.IINTERFACE.ISongDataAccess;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SongDAO implements ISongDataAccess {

    private static final String SONGS_FILE = "data/song_titles.txt";

    protected List<Song> songList;
    protected File inputFile;

    /**
     * tries to get all songs
     * @return A list of songs, a empty list if no songs exist
     * @throws IOException if something went wrong.
     */
    public List<Song> getAllSongs() throws IOException{
        List<Song> allSongs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File(SONGS_FILE))))
        {
            boolean hasLines = true;
            while(hasLines){
                String line = br.readLine();
                if(line == null)
                    hasLines = false;

                if(hasLines && !line.isBlank())
                {
                    String[] separatedLine = line.split("|");

                    int id = Integer.parseInt(separatedLine[0]);
                    String title = separatedLine[1];
                    String filePath = separatedLine[2];

                    Song song = new Song(id, title, filePath);
                    allSongs.add(song);
                }
            }
        }
        return allSongs;

    }

    /**
     * Tries to load all playlists
     * @param path the path of the playlist file
     */
    public void loadPlaylist(String path) {
        File file = new File(path);
        if (file.exists()) {
            inputFile = file;
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                var songs = objectMapper.readValue(file, new TypeReference<List<Song>>() {
                });
                this.songList.addAll(songs);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Tries to find a song match
     * @param name the name of the sing
     * @return if the song exists
     */
    public boolean hasSong(String name) {
        return getSong(name) != null;
    }

    /**
     * Tries to get a song
     * @param name the name of the song
     * @return the song
     */
    public Song getSong(String name) {
        for (int i = 0; i < songList.size(); i++) {
            var song = songList.get(i);
            if (song.equals(name)) return song;
        }
        return null;
    }

    /**
     * Tries to remove a song
     * @param name the name of the song
     */
    public void removeSong(String name) {
        for (int i = 0; i < songList.size(); i++) {
            var song = songList.get(i);
            if (song.equals(name)) songList.remove(song);
        }
    }

    /**
     * Tries to add a song
     * @param title the  title of the song
     * @param id the id of the song
     * @param filePath the filepath of the song
     */
    public void addSong(String title, int id, String filePath) {
        if (!hasSong(title)) songList.add(new Song(id, title, filePath));
    }

    /**
     * Tries to save the changes
     */
    public void save() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(inputFile, songList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tries to save the changes at the path
     * @param path the path at which you want to save.
     */
    public void save(String path) {
        var file = new File(path);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(file, songList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all songs
     * @return a list of songs
     */
    public List<Song> getSongList() {
        return songList;
    }

    /**
     * gets the input file
     * @return the input file.
     */
    public File getInputFile() {
        return inputFile;
    }
}
