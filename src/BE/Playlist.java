package BE;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    public String playListName;
    private int playlistId;
    private List<Song> songList;
    public static final String seperator = "|";

    public Playlist() {
        initialize();
    }

    public Playlist(int id, String playListName) {
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
        songList = new ArrayList();
    }

    public String getPlayListName() {
        return playListName;
    }

    public void setPlayListName(String playListName) {
        this.playListName = playListName;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        playlistId = playlistId;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void addSong(Song song) {
        songList.add(song);
    }

    public void addSongs(List<Song> songs) {
        for (int i = 0; i < songs.size(); i++) {
            var song = songs.get(i);
            if (songList.contains(song))
                songList.add(song);
        }
    }

    public void removeSong(Song song) {
        songList.remove(song);
    }

    public void editSong(Song newSong, Song oldSong) {
        removeSong(oldSong);
        addSong(newSong);
    }

    public StringProperty toStringProperty(){
        return new SimpleStringProperty(this.playListName);
    }

}
