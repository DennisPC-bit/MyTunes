package BE;

import BLL.PlaylistManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private String playListName;
    private StringProperty playListNameProperty;
    private ObjectProperty<Integer> playlistSize = new SimpleObjectProperty<>();
    private int playlistId;
    private List<Song> songList;
    private static final String seperator = "|";

    /**
     *
     */
    public Playlist() {
        initialize();
    }

    /**
     *
     * @param id
     * @param playListName
     */
    public Playlist(int id, String playListName) {
        initialize();
        setPlaylistId(id);
        setPlayListName(playListName);
    }

    /**
     *
     * @param playListName
     */
    public Playlist(String playListName) {
        initialize();
        setPlayListName(playListName);
    }

    /**
     *
     * @param playListName
     * @param songs
     */
    public Playlist(String playListName, List<Song> songs) {
        initialize();
        setPlayListName(playListName);
        addSongs(songs);
    }

    /**
     *
     */
    private void initialize() {
        songList = new ArrayList<>();
        playListNameProperty = new SimpleStringProperty();
    }

    /**
     * Get the value of name
     * @return the value of name
     */
    public String getPlayListName() {
        return playListName;
    }

    /**
     * Get the value of name property
     * @return the value of name property
     */
    public StringProperty getPlayListNameProperty() {
        return playListNameProperty;
    }

    /**
     * Set the value of name.
     * @param playListName new value of name
     */
    public void setPlayListName(String playListName) {
        this.playListName = playListName;
        this.playListNameProperty.setValue(playListName);
    }

    /**
     * Get the value of id
     * @return the value of id
     */
    public int getPlaylistId() {
        return playlistId;
    }

    /**
     * Set the value of id
     * @param playlistId new value of id
     */
    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    /**
     *
     * @return songList
     */
    public List<Song> getSongList() {
        return this.songList;
    }

    /**
     * If songlist does not have this song, it will add it.
     * @param song
     */
    public void addSong(Song song) {
        if(!songList.contains(song))
        this.songList.add(song);
    }

    /**
     * Adds song to songlist if its not already on.
     * @param songs
     */
    public void addSongs(List<Song> songs) {
        songs.forEach(song->{
            if(!this.songList.contains(song))
            songList.add(song);
        });
    }

    /**
     * Removes song from songlist
     * @param song
     */
    public void removeSong(Song song) {
        songList.remove(song);
    }

    /**
     * Removes the old song, and replaces it with the edited one.
     * @param newSong
     * @param oldSong
     */
    public void editSong(Song newSong, Song oldSong) {
        removeSong(oldSong);
        addSong(newSong);
    }

    public ObjectProperty<Integer> getPlaylistSize(){
        PlaylistManager playlistManager = new PlaylistManager();
        try {
            playlistSize.setValue(playlistManager.loadSongsOnPlaylist(this.getPlaylistId()).size());
            return playlistSize;
        } catch (Exception e) {
            e.printStackTrace();
            return new SimpleObjectProperty<>(0);
        }
    }
}
