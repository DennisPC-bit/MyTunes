package BE;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private String playListName;
    private StringProperty playListNameProperty;
    private int playlistId;
    private List<Song> songList;
    private static final String seperator = "|";

    public Playlist() {
        initialize();
    }

    public Playlist(int id, String playListName) {
        initialize();
        setPlaylistId(id);
        setPlayListName(playListName);
    }

    public Playlist(String playListName) {
        initialize();
        setPlayListName(playListName);
    }

    public Playlist(String playListName, List<Song> songs) {
        initialize();
        setPlayListName(playListName);
        addSongs(songs);
    }

    private void initialize() {
        songList = new ArrayList<>();
        playListNameProperty = new SimpleStringProperty();
    }

    public String getPlayListName() {
        return playListName;
    }

    public StringProperty getPlayListNameProperty() {
        return playListNameProperty;
    }

    public void setPlayListName(String playListName) {
        this.playListName = playListName;
        this.playListNameProperty.setValue(playListName);
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public List<Song> getSongList() {
        return this.songList;
    }

    public void addSong(Song song) {
        if(!songList.contains(song))
        this.songList.add(song);
    }

    public void addSongs(List<Song> songs) {
        songs.forEach(song->{
            if(!this.songList.contains(song))
            songList.add(song);
        });
    }

    public void removeSong(Song song) {
        songList.remove(song);
    }

    public void editSong(Song newSong, Song oldSong) {
        removeSong(oldSong);
        addSong(newSong);
    }
}
