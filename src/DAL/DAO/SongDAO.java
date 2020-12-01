package DAL.DAO;

import BE.Song;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SongDAO {
    protected List<Song> songList;
    protected File inputFile;

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

    public boolean hasSong(String name) {
        return getSong(name) != null;
    }

    public Song getSong(String name) {
        for (int i = 0; i < songList.size(); i++) {
            var song = songList.get(i);
            if (song.equals(name)) return song;
        }
        return null;
    }

    public void removeSong(String name) {
        for (int i = 0; i < songList.size(); i++) {
            var song = songList.get(i);
            if (song.equals(name)) songList.remove(song);
        }
    }

    public void addSong(String title, String filePath) {
        if (!hasSong(title)) songList.add(new Song(title, filePath));
    }

    public void save() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(inputFile, songList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(String path) {
        var file = new File(path);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(file, songList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Song> getSongList() {
        return songList;
    }

    public File getInputFile() {
        return inputFile;
    }
}
