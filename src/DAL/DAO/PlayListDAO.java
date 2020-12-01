package DAL.DAO;

import BE.Playlist;
import BE.Song;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.List;

public class PlayListDAO {

    protected List<Playlist> playlists;
    protected File inputFile;

    public void loadPlaylist(String path) {
        File file = new File(path);
        if (file.exists()) {
            inputFile = file;
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                var playlists = objectMapper.readValue(file, new TypeReference<List<Playlist>>() {
                });
                this.playlists.addAll(playlists);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean hasPlaylist(String name) {
        return getPlaylist(name) != null;
    }

    public Playlist getPlaylist(String name) {
        for (int i = 0; i < playlists.size(); i++) {
            var playlist = playlists.get(i);
            if (playlists.equals(name)) return playlist;
        }
        return null;
    }

    public void deletePlaylist(String name) {
        for (int i = 0; i < playlists.size(); i++) {
            var playlist = playlists.get(i);
            if (playlists.equals(name)) playlists.remove(playlist);
        }
    }

    public void createPlaylist(String name) {
        if (!hasPlaylist(name)) playlists.add(new Playlist(name));
    }

    public void createPlaylist(String name, List<Song> songs) {
        if (!hasPlaylist(name)) playlists.add(new Playlist(name, songs));
    }

    public void save() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(inputFile, playlists);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(String path) {
        var file = new File(path);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(file, playlists);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Playlist> getPlaylistCollection() {
        return playlists;
    }

    public File getInputFile() {
        return inputFile;
    }
}
