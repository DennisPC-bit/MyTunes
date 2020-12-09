package BE;

import BLL.PlaylistManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Playlist {

    private String playListName;
    private StringProperty playListNameProperty;
    private ObjectProperty<Integer> playlistSize = new SimpleObjectProperty<>();
    private int playlistId;

    /**
     * Constructor with id and playlistName
     * @param id
     * @param playListName
     */
    public Playlist(int id, String playListName) {
        initialize();
        setPlaylistId(id);
        setPlayListName(playListName);
    }

    /**
     * Constructor with playlistName
     * @param playListName
     */
    public Playlist(String playListName) {
        initialize();
        setPlayListName(playListName);
    }

    /**
     *  initializes the variables
     */
    private void initialize() {
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
