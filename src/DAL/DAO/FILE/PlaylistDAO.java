package DAL.DAO.FILE;

import BE.Playlist;
import BE.Song;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.List;

public class PlaylistDAO {

    protected List<Playlist> playlists;
    protected File inputFile;

    /**
     * Tries to load the playlists at the given path.
     * @param path the path of the playlist file.
     */
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

    /**
     * checks if the playlist exists
     * @param name the name of the playlist
     * @return if the playlist exists or not
     */
    public boolean hasPlaylist(String name) {
        return getPlaylist(name) != null;
    }

    /**
     * Tries to get a playlist
     * @param name the name of the playlist
     * @return null if no match, a playlist, if it exists
     */
    public Playlist getPlaylist(String name) {
        for (int i = 0; i < playlists.size(); i++) {
            var playlist = playlists.get(i);
            if (playlists.equals(name)) return playlist;
        }
        return null;
    }

    /**
     * tries to delete a playlist.
     * @param name name of the playlist.
     */
    public void deletePlaylist(String name) {
        for (int i = 0; i < playlists.size(); i++) {
            var playlist = playlists.get(i);
            if (playlists.equals(name)) playlists.remove(playlist);
        }
    }

    /**
     * Creates a playlist.
     * @param name the name of the playlist.
     */
    public void createPlaylist(String name) {
        if (!hasPlaylist(name)) playlists.add(new Playlist(name));
    }

    /**
     * Creates a playlist with a list of songs.
     * @param name the name of the playlist.
     * @param songs the songs in the playlist.
     */
    public void createPlaylist(String name, List<Song> songs) {
        if (!hasPlaylist(name)) playlists.add(new Playlist(name, songs));
    }

    /**
     * Tries to save the playlists
     */
    public void save() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(inputFile, playlists);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tries to save the playlists at the given path.
     * @param path the path you want to save to.
     */
    public void save(String path) {
        var file = new File(path);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(file, playlists);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tries to get the songs on the playlist
     * @return A list of playlists
     */
    public List<Playlist> getPlaylistCollection() {
        return playlists;
    }

    /**
     *
     * @return the input file.
     */
    public File getInputFile() {
        return inputFile;
    }
}
